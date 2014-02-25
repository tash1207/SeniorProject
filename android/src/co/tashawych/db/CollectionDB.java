package co.tashawych.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.tashawych.datatypes.Collection;

public class CollectionDB {
	
	private static final String TABLE_NAME = "collection";
	public static final String COL_ID = "_id";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_TITLE = "title";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_CATEGORY = "category";
	public static final String COL_IS_PRIVATE = "is_private";
	public static final String COL_PICTURE = "picture";
	public static final String COL_FAVORITES = "num_favorites";
	
	public static void createTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" 
				+ COL_ID + " INTEGER PRIMARY KEY, " 
				+ COL_USER_ID + " INTEGER, " 
				+ COL_TITLE + " TEXT, "
				+ COL_DESCRIPTION + " TEXT, "
				+ COL_CATEGORY + " TEXT, "
				+ COL_IS_PRIVATE + " BOOLEAN, "
				+ COL_PICTURE + " TEXT, "
				+ COL_FAVORITES + " INTEGER)";
		db.execSQL(CREATE_TABLE);
	}

	public static void insert(SQLiteDatabase db, Collection collection) {
		db.insert(TABLE_NAME, null, collection.toContentValues());
	}
	
	public static Cursor getCollectionsByUserId(SQLiteDatabase db, int user_id) {
		return db.query(TABLE_NAME, null, COL_USER_ID + " = " + user_id, null, null, null, null);
	}

}