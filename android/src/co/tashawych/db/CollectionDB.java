package co.tashawych.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.tashawych.datatypes.Collection;

public class CollectionDB {
	
	private static final String TABLE_NAME = "collection";
	public static final String COL_ID = "_id";
	public static final String COL_USERNAME = "username";
	public static final String COL_TITLE = "title";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_CATEGORY = "category";
	public static final String COL_IS_PRIVATE = "is_private";
	public static final String COL_PICTURE = "picture";
	public static final String COL_FAVORITES = "num_favorites";
	
	public static void createTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" 
				+ COL_ID + " TEXT PRIMARY KEY, " 
				+ COL_USERNAME + " TEXT, " 
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
	
	public static Cursor getCollections(SQLiteDatabase db) {
		return db.query(TABLE_NAME, null, null, null, null, null, COL_TITLE + " ASC");
	}
	
	public static Cursor getCollectionsByUserId(SQLiteDatabase db, String username) {
		return db.query(TABLE_NAME, null, COL_USERNAME + "=?", new String[]{username}, null, null, COL_TITLE + " ASC");
	}
	
	public static Collection getCollection(SQLiteDatabase db, String id) {
		Cursor c = db.query(TABLE_NAME, null, COL_ID + "=?", new String[]{id}, null, null, null);
		c.moveToFirst();
		return new Collection(c);
	}

	public static void updateCollection(SQLiteDatabase db, Collection col) {
		db.update(TABLE_NAME, col.toContentValues(), COL_ID + "=?", new String[] { col.getId() });
	}

	public static void removeCollection(SQLiteDatabase db, String col_id) {
		db.delete(TABLE_NAME, COL_ID + "=?", new String[]{col_id});
	}

}
