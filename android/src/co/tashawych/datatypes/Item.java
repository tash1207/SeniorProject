package co.tashawych.datatypes;

import co.tashawych.db.ItemDB;
import android.content.ContentValues;
import android.database.Cursor;

public class Item {

	private String id;
	private String collection_id;
	private String title;
	private String description;
	private String picture;

	public Item(String collection_id, String title, String description, String picture) {
		this.collection_id = collection_id;
		this.title = title;
		this.description = description;
		this.picture = picture;
	}

	public Item(Cursor c) {
		this.id = c.getString(c.getColumnIndex(ItemDB.ITEM_ID));
		this.collection_id = c.getString(c.getColumnIndex(ItemDB.ITEM_COL_ID));
		this.title = c.getString(c.getColumnIndex(ItemDB.ITEM_TITLE));
		this.description = c.getString(c.getColumnIndex(ItemDB.ITEM_DESCRIPTION));
		this.picture = c.getString(c.getColumnIndex(ItemDB.ITEM_PICTURE));
	}

	public ContentValues toContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(ItemDB.ITEM_ID, id);
		cv.put(ItemDB.ITEM_COL_ID, collection_id);
		cv.put(ItemDB.ITEM_TITLE, title);
		cv.put(ItemDB.ITEM_DESCRIPTION, description);
		cv.put(ItemDB.ITEM_PICTURE, picture);
		return cv;
	}

	// GETTERS AND SETTERS

	public String getId() {
		return id;
	}

	public String getCollectionId() {
		return collection_id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getPicture() {
		return picture;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCollectionId(String id) {
		this.collection_id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
