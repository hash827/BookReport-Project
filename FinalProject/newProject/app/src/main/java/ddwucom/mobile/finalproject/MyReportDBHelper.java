package ddwucom.mobile.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyReportDBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "reports.db";

    public final static String TABLE_NAME = "myReport_table";
    public final static String COL_ID = "_id";
    public final static String COL_DATE = "date";
    public final static String COL_PAGE= "page";
    public final static String COL_ISBN= "isbn";
    public final static String COL_BOOKID = "bookid";
    public final static String COL_REPORTCONTENT= "reportcontent";


    public MyReportDBHelper(Context context){super(context, DB_NAME, null, 1);}
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
               COL_ISBN + " TEXT, " +COL_DATE + " TEXT, "+ COL_PAGE + " TEXT, " +
                COL_BOOKID + " TEXT, "+
                COL_REPORTCONTENT + " TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
