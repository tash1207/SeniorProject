package co.tashawych.datatypes;

import co.tashawych.db.CollectionDB;
import android.content.ContentValues;
import android.database.Cursor;

public class Collection {
	
	private int id;
	private String username;
	private String title;
	private String description;
	private String category;
	private boolean is_private;
	private String picture;
	private int num_favorites;
	
	public Collection(int id, String username, String title, String description, String category, 
			boolean is_private, String picture) {
		this.id = id;
		this.username = username; //APIHandler.getUsername();
		this.title = title;
		this.description = description;
		this.category = category;
		this.is_private = is_private;
		this.picture = picture;
	}
	
	public Collection(Cursor c) {
		this.id = c.getInt(c.getColumnIndex(CollectionDB.COL_ID));
		this.username = c.getString(c.getColumnIndex(CollectionDB.COL_USERNAME));
		this.title = c.getString(c.getColumnIndex(CollectionDB.COL_TITLE));
		this.description = c.getString(c.getColumnIndex(CollectionDB.COL_DESCRIPTION));
		this.category = c.getString(c.getColumnIndex(CollectionDB.COL_CATEGORY));
		this.is_private = c.getInt(c.getColumnIndex(CollectionDB.COL_IS_PRIVATE)) > 0;
		this.picture = c.getString(c.getColumnIndex(CollectionDB.COL_PICTURE));
		this.num_favorites = c.getInt(c.getColumnIndex(CollectionDB.COL_FAVORITES));
	}
	
	public ContentValues toContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(CollectionDB.COL_ID, id);
		cv.put(CollectionDB.COL_USERNAME, username);
		cv.put(CollectionDB.COL_TITLE, title);
		cv.put(CollectionDB.COL_DESCRIPTION, description);
		cv.put(CollectionDB.COL_CATEGORY, category);
		cv.put(CollectionDB.COL_IS_PRIVATE, is_private);
		cv.put(CollectionDB.COL_PICTURE, picture);
		cv.put(CollectionDB.COL_FAVORITES, num_favorites);
		return cv;
	}
	
	// GETTERS AND SETTERS
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCategory() {
		return category;
	}
	
	public boolean getIsPrivate() {
		return is_private;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public int getNumFavorites() {
		return num_favorites;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setIsPrivate(boolean is_private) {
		this.is_private = is_private;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public void setNumFavorites(int num_favorites) {
		this.num_favorites = num_favorites;
	}

}
