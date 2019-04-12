package khayreey.khayreey.waitlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import  khayreey.khayreey.waitlist.data.Contract.entry;
public class DBHhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="waitList.db";
    private static final int DATABASE_VERSION = 1;


    public DBHhelper( Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_WAITLIST_TABLE="CREATE TABLE  " +
                entry.TABLE_NAME +
                "(" + entry . _ID + "  INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +entry.COLUMN_GUEST_NAME + " TEXT NOT NULL , "+
                entry.COLUMN_PARTY_SIZE + " TEXT NOT NULL ,  "+
                entry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+" );";

        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS "+entry.TABLE_NAME);
        onCreate(db);
    }
}
