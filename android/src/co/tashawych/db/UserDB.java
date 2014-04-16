package co.tashawych.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.tashawych.datatypes.User;

public class UserDB {
	private static final String TABLE_NAME = "user";
	public static final String USER_ID = "_id";
	public static final String USER_DISPLAY_NAME = "display_name";
	public static final String USER_EMAIL = "email";
	public static final String USER_PICTURE = "picture";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" 
				+ USER_ID + " TEXT PRIMARY KEY, " 
				+ USER_DISPLAY_NAME + " TEXT, " 
				+ USER_EMAIL + " TEXT, "
				+ USER_PICTURE + " TEXT)";
		db.execSQL(CREATE_TABLE);
	}

	public static void insert(SQLiteDatabase db, User user) {
		db.insert(TABLE_NAME, null, user.toContentValues());
	}

	public static User getUser(SQLiteDatabase db, String id) {
		Cursor c = db.query(TABLE_NAME, null, USER_ID + "=?", new String[] { id }, null, null, null);
		c.moveToFirst();
		return new User(c);
	}

	public static void updateUser(SQLiteDatabase db, User user) {
		db.update(TABLE_NAME, user.toContentValues(), USER_ID + "=?", new String[] { user.getUsername() });
	}

	public static void removeAllUsers(SQLiteDatabase db) {
		db.delete(TABLE_NAME, null, null);
	}

}
