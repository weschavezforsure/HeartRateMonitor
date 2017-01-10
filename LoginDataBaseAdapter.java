package edu.pdx.jwaldrip.ece558f16.project4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;

/**
 * Code source: Hiral Barot
 * Modified by Jonathan Waldrip
 *
 */
public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "ResultsDatabase.db";
    static final int DATABASE_VERSION = 1;
    //public static final int NAME_COLUMN = 1;
    private static long mResultCount = 1;

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE =
            "create table "+"HISTORY"+"( " +"ID"+" integer primary key autoincrement,"+ "NUMBER  text, TIMESTAMP text, HEART_RATE text); ";

    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public void clearAll()
    {
        db.delete("HISTORY", null, null);
        mResultCount = 1;
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String heartRate, String timestamp)
    {

        // Advance counter to current row
        long numRows = DatabaseUtils.queryNumEntries(db, "HISTORY");
        if (numRows >= mResultCount) {
            mResultCount = numRows + 1;
        }

        ContentValues newValues = new ContentValues();

        // Assigning values for each row.
        newValues.put("NUMBER", String.valueOf(mResultCount));
        newValues.put("TIMESTAMP", timestamp);
        newValues.put("HEART_RATE", heartRate);


        // Insert the row into table
        db.insert("HISTORY", null, newValues);

        mResultCount++;
    }

    public String getEntry(String number)
    {
        // Query database, return cursor focused on a single row of database
        // row is selected by number passed into this method
        Cursor dbCursor=db.query("HISTORY", null, " NUMBER=?", new String[]{number}, null, null, null);

        // If row is empty
        if(dbCursor.getCount()<1)
        {
            dbCursor.close();
            // let ResultListFragment know that it has reached the end of the results list
            return "Not Yet Recorded";
        }

        // otherwise, return a single string that contains the heart rate with the time stamp
        dbCursor.moveToFirst();
        String heartRate = dbCursor.getString(dbCursor.getColumnIndex("HEART_RATE"));
        String timestamp = dbCursor.getString(dbCursor.getColumnIndex("TIMESTAMP"));
        String tempStr = timestamp + "         Heart Rate = " + heartRate;
        dbCursor.close();
        return tempStr;

    }
}
