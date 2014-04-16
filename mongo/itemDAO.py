import pymongo
from bson.objectid import ObjectId

# The Item Data Access Object handles interactions with the Items table

class ItemDAO:
  def __init__(self, database):
    self.db = database
    self.items = database.items

  def insert_item(self, title, description, picture, col_id):
    item = {"title": title, 
      "description": description, 
      "picture": picture,
      "collection_id": col_id}

    try:
      id = self.items.insert(item);
      return str(id)
    except:
      print "Error inserting the item"
      return ""

  def get_items(self, col_id):
    cursor = self.items.find({"collection_id": col_id}, {'title': 'true', 'description': 'true', 'picture': 'true'});

    a = []

    for item in cursor:
      if 'title' not in item:
        item['title'] = []
      if 'description' not in item:
        item['description'] = []
      if 'picture' not in item:
        item['picture'] = []

      a.append({'_id':item['_id'], 'title':item['title'], 
        'description':item['description'], 'picture':item['picture'], 'collection_id':col_id })

    return a
