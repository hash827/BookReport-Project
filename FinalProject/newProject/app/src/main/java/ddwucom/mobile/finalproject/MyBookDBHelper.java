package ddwucom.mobile.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyBookDBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "books.db";

    public final static String TABLE_NAME = "myBook_table";
    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_AUTHOR= "author";
    public final static String COL_ISBN= "isbn";
    public final static String COL_PUBLISHER= "publisher";
    public final static String COL_IMAGE= "image";
    public final static String COL_CONTENT = "content";
    public final static String COL_STARTDATE ="startdate";
    public final static String COL_ENDDATE ="enddate";
    public final static String COL_RATING = "rating";
    public final static String COL_BOOKSTATE = "bookstate";

    public MyBookDBHelper(Context context){super(context, DB_NAME, null, 1);}
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
               COL_TITLE + " TEXT, " + COL_AUTHOR + " TEXT, " +COL_ISBN + " TEXT, "
                +COL_PUBLISHER + " TEXT, " +COL_IMAGE + " TEXT, " +COL_CONTENT + " TEXT, "
                +COL_STARTDATE + " TEXT, "+ COL_ENDDATE + " TEXT, " + COL_RATING + " TEXT, " +
                COL_BOOKSTATE + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
