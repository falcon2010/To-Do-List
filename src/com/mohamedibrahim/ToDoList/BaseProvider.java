package com.mohamedibrahim.ToDoList;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract.Contacts.Data;
import android.text.TextUtils;

public class BaseProvider extends ContentProvider {

	private static final int ALL_ROWS = 1;
	private static final int SINGLE_ROW = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(DATABASE.STRING_URI, DATABASE.CONTENT_NAME, ALL_ROWS);
		uriMatcher.addURI(DATABASE.STRING_URI, DATABASE.CONTENT_NAME + "/#",
				SINGLE_ROW);

	}

	private MySQLiteOpenHelper myOpenHelper;

	@Override
	public boolean onCreate() {

		myOpenHelper = new MySQLiteOpenHelper(getContext(),
				DATABASE.DATABASE_NAME, null, DATABASE.DATABASE_VERSION);

		return true;

	}

	@Override
	public String getType(Uri uri) {

		switch (uriMatcher.match(uri)) {
		case ALL_ROWS:
			return "vnd.android.cursor.dir/vnd.paad.todos";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.paad.todos";
		default:
			throw new IllegalArgumentException("Unsupported URI:" + uri);
		}

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:

			String rowID = uri.getPathSegments().get(1);
			selection = DATABASE.KEY_ID
					+ "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");

			break;

		}

		int deleteCount = db.delete(DATABASE.DATABASE_TABLE, selection,
				selectionArgs);
		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);

		return deleteCount;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		String nullColumnHack = null;

		long id = db.insert(DATABASE.DATABASE_TABLE, nullColumnHack, values);

		if (id > -1) {
			Uri insertedUri = ContentUris.withAppendedId(uri, id);
			getContext().getContentResolver().notifyChange(insertedUri, null);
			return insertedUri;
		} else

			return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		String groupBy = null;
		String having = null;
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(DATABASE.DATABASE_TABLE);

		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:

			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(DATABASE.KEY_ID + " = " + rowID);
			break;

		}

		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, having, sortOrder);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowId = uri.getPathSegments().get(1);
			selection = DATABASE.KEY_ID
					+ " = "
					+ rowId
					+ (!TextUtils.isEmpty(selection) ? "AND (" + selection
							+ ')' : "");
			break;
		}

		// perform update

		int updateCount = db.update(DATABASE.DATABASE_TABLE, values, selection,
				selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);

		return updateCount;
	}

	/**
	 * private SQliteOpenHelper class
	 */

	private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

		public MySQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE.DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DATABASE.DATABASE_DROP);
			onCreate(db);

		}

	}

	/**
	 * this class contians all constants in related to database and provider
	 * class
	 * 
	 * @author MohamedIbrahim
	 * 
	 */
	public static class DATABASE {

		public static final String STRING_URI = "com.mohamedibrahim.ToDoList";
		public static final String CONTENT_NAME = "todoitems";

		public static final Uri CONTENT_URI = Uri
				.parse("content://com.mohamedibrahim.ToDoList/todoitems");

		public static final String KEY_ID = "_id";
		public static final String KEY_TASK = "task";
		public static final String KEY_CREATION_DATE = "creation_date";

		private static final String DATABASE_NAME = "todoDatabase.db";
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_TABLE = "todoItemTable";

		private static final String DATABASE_CREATE = "create table "
				+ DATABASE_TABLE + " (" + KEY_ID
				+ " integer primary key autoincrement, " + KEY_TASK
				+ " text not null, " + KEY_CREATION_DATE + " long );";

		private static final String DATABASE_DROP = "DROP TABLE IF IT EXISTS "
				+ DATABASE_TABLE;

	}

}
