package co.tashawych.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.tashawych.datatypes.Item;

public class ItemDB {

	private static final String TABLE_NAME = "item";
	public static final String ITEM_ID = "_id";
	public static final String ITEM_COL_ID = "collection_id";
	public static final String ITEM_TITLE = "title";
	public static final String ITEM_DESCRIPTION = "description";
	public static final String ITEM_PICTURE = "picture";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" 
				+ ITEM_ID + " TEXT PRIMARY KEY, " 
				+ ITEM_COL_ID + " TEXT, " 
				+ ITEM_TITLE + " TEXT, "
				+ ITEM_DESCRIPTION + " TEXT, "
				+ ITEM_PICTURE + " TEXT)";
		db.execSQL(CREATE_TABLE);
	}

	public static void insert(SQLiteDatabase db, Item item) {
		db.insert(TABLE_NAME, null, item.toContentValues());
	}

	public static Item getItem(SQLiteDatabase db, String id) {
		Cursor c = db.query(TABLE_NAME, null, ITEM_ID + "=?", new String[]{id}, null, null, null);
		c.moveToFirst();
		return new Item(c);
	}

	public static Cursor getItemsByCollectionId(SQLiteDatabase db, String col_id) {
		return db.query(TABLE_NAME, null, ITEM_COL_ID + "=?", new String[] { col_id }, null, null, ITEM_TITLE + " ASC");
	}

	public static void removeItem(SQLiteDatabase db, String id) {
		db.delete(TABLE_NAME, ITEM_ID + "=?", new String[]{id});
	}
}
