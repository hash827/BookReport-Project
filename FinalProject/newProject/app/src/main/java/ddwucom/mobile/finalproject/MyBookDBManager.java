package ddwucom.mobile.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MyBookDBManager {

    MyBookDBHelper bookDBHelper = null;
    Cursor cursor = null;

    public MyBookDBManager(Context context){
        bookDBHelper = new MyBookDBHelper(context);
    }

    public boolean addNewBook(MyBook newBook){
        SQLiteDatabase db = bookDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MyBookDBHelper.COL_AUTHOR, newBook.getAuthor());
        value.put(MyBookDBHelper.COL_TITLE,newBook.getTitle());
        value.put(MyBookDBHelper.COL_ISBN, newBook.getIsbn());
        value.put(MyBookDBHelper.COL_PUBLISHER,newBook.getPublisher());
        value.put(MyBookDBHelper.COL_IMAGE,newBook.getImage());

        long count = db.insert(MyBookDBHelper.TABLE_NAME, null, value);
        bookDBHelper.close();
        if (count > 0) return true;

        return false;
    }
    public ArrayList<MyBook> getAllProBook(){
        ArrayList list = new ArrayList();

        SQLiteDatabase db = bookDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyBookDBHelper.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex( MyBookDBHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_TITLE));
            String author = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_AUTHOR));
            String isbn = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_ISBN));
            String image = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_IMAGE));
            String publisher = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_PUBLISHER));

            list.add(new MyBook(id, title, author, isbn, publisher, image));
        }
        cursor.close();
        bookDBHelper.close();
        return list;
    }
    public MyBook getBook(long id){
        SQLiteDatabase db = bookDBHelper.getReadableDatabase();
        MyBook myBook = null;

        String selection = "_id=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = db.query(MyBookDBHelper.TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_TITLE));
            String author = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_AUTHOR));
            String isbn = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_ISBN));
            String image = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_IMAGE));
            String publisher = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_PUBLISHER));
            String content = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_CONTENT));
            String startDate = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_STARTDATE));
            String endDate = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_ENDDATE));
            String rating = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_RATING));
            String bookState = cursor.getString(cursor.getColumnIndex(MyBookDBHelper.COL_BOOKSTATE));

            myBook = new MyBook(id, title,author,isbn, publisher,image,content, startDate,endDate,rating,bookState);
        }
        cursor.close();
        bookDBHelper.close();

        return myBook;
    }
    public boolean updateBook(MyBook myBook){
        SQLiteDatabase db = bookDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(MyBookDBHelper.COL_CONTENT, myBook.getContent());
        row.put(MyBookDBHelper.COL_STARTDATE, myBook.getStartDate());
        row.put(MyBookDBHelper.COL_ENDDATE, myBook.getEndDate());
        row.put(MyBookDBHelper.COL_RATING, myBook.getRating());
        row.put(MyBookDBHelper.COL_BOOKSTATE, myBook.getBookState());

        String whereClause = MyBookDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(myBook.get_id())};

        int result = db.update(MyBookDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        db.close();
        if (result > 0) return true;
        return false;

    }
    public boolean removeBook(long _id){
        SQLiteDatabase db = bookDBHelper.getWritableDatabase();
        String whereclause = bookDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        int result = db.delete(MyBookDBHelper.TABLE_NAME, whereclause, whereArgs);
        bookDBHelper.close();
        if (result > 0)
            return true;

        return false;

    }


}
