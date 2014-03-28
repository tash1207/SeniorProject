import pymongo
from bson.objectid import ObjectId

# The Collection Data Access Object handles interactions with the Collections table

class CollectionDAO:

	def __init__(self, database):
		self.db = database
		self.collections = database.collections

	def insert_collection(self, title, description, category, picture, is_private, username):
		collection = {"title": title, 
			"description": description, 
			"category": category,
			"is_private": is_private,
			"username": username}

		if picture != "":
			collection['picture'] = picture

		try:
			id = self.collections.insert(collection);
			return str(id)
		except:
			print "Error inserting the collection"
			print sys.exc_info()[0]
			return ""

