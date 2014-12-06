package com.DMSSS.RouteMandu;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class RManduProvider extends ContentProvider {

	private static final String AUTHORITY = "com.DMSSS.RouteMandu.RManduProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + MySQLiteHelper.TABLE_NAME);
	private MySQLiteHelper helper;

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.insert(MySQLiteHelper.TABLE_NAME, null, contentValues);
		return null;
	}

	@Override
	public boolean onCreate() {
		helper = new MySQLiteHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] columns, String selection,
			String[] arg3, String arg4) {
		SQLiteDatabase db = helper.getReadableDatabase();

		String WHERE = "";
		if (selection == null)
			WHERE = null;
		else
			WHERE = MySQLiteHelper.COLUMN_PLACE + " = '" + selection + "'";
		return db.query(MySQLiteHelper.TABLE_NAME, columns, WHERE, null, null,
				null, null);
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.update(MySQLiteHelper.TABLE_NAME, contentValues, null, null);
		return 0;
	}
}
