package ddwucom.mobile.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MyReportDBManager {

    MyReportDBHelper reportDBHelper = null;
    Cursor cursor = null;

    public MyReportDBManager(Context context) {
        reportDBHelper = new MyReportDBHelper(context);
    }

    public boolean addNewReport(MyReport newReport) {
        SQLiteDatabase db = reportDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MyReportDBHelper.COL_ISBN, newReport.getIsbn());
        value.put(MyReportDBHelper.COL_BOOKID, newReport.getBookId());
        value.put(MyReportDBHelper.COL_DATE, newReport.getDate());
        value.put(MyReportDBHelper.COL_PAGE, newReport.getPage());
        value.put(MyReportDBHelper.COL_REPORTCONTENT, newReport.getReportContent());

        long count = db.insert(MyReportDBHelper.TABLE_NAME, null, value);
        reportDBHelper.close();
        if (count > 0) return true;

        return false;
    }

    public ArrayList<MyReport> getAllReport(String bookId) {
        ArrayList list = new ArrayList();
         String selection = "bookid=?";
         String[] selectionArgs = new String[]{bookId};
        SQLiteDatabase db = reportDBHelper.getReadableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM " + MyReportDBHelper.TABLE_NAME, null);
        Cursor cursor = db.query(MyReportDBHelper.TABLE_NAME, null,selection,
                selectionArgs,
               null,null,null,null);
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(MyReportDBHelper.COL_ID));
            String isbn = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_ISBN));
            String page = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_PAGE));
            String date = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_DATE));
            String reportContent = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_REPORTCONTENT));

            list.add(new MyReport(id, Long.parseLong(bookId),isbn,date,page,reportContent));
        }
        cursor.close();
        reportDBHelper.close();
        return list;
    }
    public MyReport getReport(long id){
        SQLiteDatabase db = reportDBHelper.getReadableDatabase();
        MyReport myReport = null;

        String selection = "_id=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = db.query(MyReportDBHelper.TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_REPORTCONTENT));
            String page = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_PAGE));
            String isbn = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_ISBN));
            String date = cursor.getString(cursor.getColumnIndex(MyReportDBHelper.COL_DATE));

            myReport = new MyReport(id, isbn,date,page, content);
        }
        cursor.close();
        reportDBHelper.close();

        return myReport;
    }
    public boolean updateReport(MyReport myReport){
        SQLiteDatabase db = reportDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(MyReportDBHelper.COL_REPORTCONTENT, myReport.getReportContent());
        row.put(MyReportDBHelper.COL_PAGE,myReport.getPage());
        row.put(MyReportDBHelper.COL_DATE, myReport.getDate());

        String whereClause = MyReportDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(myReport.get_id())};

        int result = db.update(MyReportDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        db.close();
        if (result > 0) return true;
        return false;

    }
    public boolean removeReport(long _id){
        SQLiteDatabase db = reportDBHelper.getWritableDatabase();
        String whereclause = MyReportDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        int result = db.delete(MyReportDBHelper.TABLE_NAME, whereclause, whereArgs);
        reportDBHelper.close();
        if (result > 0)
            return true;

        return false;

    }
    public boolean removeReportByBookId(long _id){
        SQLiteDatabase db = reportDBHelper.getWritableDatabase();
        String whereclause = MyReportDBHelper.COL_BOOKID + "=?";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        int result = db.delete(MyReportDBHelper.TABLE_NAME, whereclause, whereArgs);
        reportDBHelper.close();
        if (result > 0)
            return true;

        return false;

    }
}