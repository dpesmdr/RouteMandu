package com.DMSSS.RouteMandu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	static final String DATABASE_NAME = "FindPlace.db";
	static final String TABLE_NAME = "Places";
	static final String COLUMN_PLACE = "Place Name";
	static final String COLUMN_LONGITUDE = "Longitude";
	static final String COLUMN_LATITUDE = "Latitude";

	private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ "(_ID INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LONGITUDE
			+ " INTEGER NOT NULL," + COLUMN_LATITUDE + " INTEGER NOT NULL)";

	// + COLUMN_PLACE + " TEXT NOT NULL)";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXIST");
		onCreate(db);
	}

}
