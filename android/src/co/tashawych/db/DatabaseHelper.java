package co.tashawych.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import co.tashawych.datatypes.Collection;
import co.tashawych.datatypes.Item;
import co.tashawych.datatypes.User;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "collector";
	private static final int DATABASE_VERSION = 1;

	protected static DatabaseHelper helper = null;
	protected static SQLiteDatabase db = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create all tables
		UserDB.createTable(db);
		CollectionDB.createTable(db);
		ItemDB.createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public static synchronized DatabaseHelper getHelper(Context context) {
		if (helper == null) {
			helper = new DatabaseHelper(context);
			db = helper.getWritableDatabase();
		}
		return helper;
	}

	// UserDB methods

	public void insertUser(User user) {
		UserDB.insert(db, user);
	}

	public User getUser(String id) {
		return UserDB.getUser(db, id);
	}

	public void updateUser(User user) {
		UserDB.updateUser(db, user);
	}

	public void removeAllUsers() {
		UserDB.removeAllUsers(db);
	}

	// CollectionDB methods

	public void insertCollection(Collection collection) {
		CollectionDB.insert(db, collection);
	}

	public Collection getCollection(String id) {
		if (id == null) return null;
		return CollectionDB.getCollection(db, id);
	}

	public Cursor getCollections() {
		return CollectionDB.getCollections(db);
	}

	public Cursor getCollectionsByUserId(String username) {
		return CollectionDB.getCollectionsByUserId(db, username);
	}

	public void updateCollection(Collection col) {
		CollectionDB.updateCollection(db, col);
	}

	// ItemDB methods

	public void insertItem(Item item) {
		ItemDB.insert(db, item);
	}

	public Item getItem(String id) {
		return ItemDB.getItem(db, id);
	}

	public Cursor getItemsByCollectionId(String col_id) {
		return ItemDB.getItemsByCollectionId(db, col_id);
	}

}
