package co.tashawych.datatypes;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import co.tashawych.db.UserDB;

public class User {

	private String username;
	private String display_name;
	private String email;
	private String picture;

	public User(String username, String display_name, String email, String picture) {
		this.username = username;
		this.display_name = display_name;
		this.email = email;
		this.picture = picture;
	}
	
	public User(JSONObject json) {
		try {
			this.username = json.getString("_id");
			this.display_name = json.has("display_name") ? json.getString("display_name") : "";
			this.email = json.getString("email");
			this.picture = json.has("picture") ? json.getString("picture") : "";
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public User(Cursor c) {
		this.username = c.getString(c.getColumnIndex(UserDB.USER_ID));
		this.display_name = c.getString(c.getColumnIndex(UserDB.USER_DISPLAY_NAME));
		this.email = c.getString(c.getColumnIndex(UserDB.USER_EMAIL));
		this.picture = c.getString(c.getColumnIndex(UserDB.USER_PICTURE));
	}

	public ContentValues toContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(UserDB.USER_ID, username);
		cv.put(UserDB.USER_DISPLAY_NAME, display_name);
		cv.put(UserDB.USER_EMAIL, email);
		cv.put(UserDB.USER_PICTURE, picture);
		return cv;
	}

	// GETTERS AND SETTERS

	public String getUsername() {
		return username;
	}

	public String getDisplayName() {
		return display_name;
	}

	public String getEmail() {
		return email;
	}

	public String getPicture() {
		return picture;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
