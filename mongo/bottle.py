import pymongo
import userDAO
import collectionDAO
import itemDAO
import bottle
import sys

@bottle.get('/login')
def login():
  return '''
    <form action="/login" method="post">
      Username: <input name="username" type="text" />
      Password: <input name="password" type="password" />
      <input value="Login" type="submit" />
    </form>
  '''

@bottle.post('/login')
def post_login():
  username = bottle.request.forms.get('username')
  password = bottle.request.forms.get('password')

  user_record = users.validate_login(username, password)

  if user_record:
    return user_record

  else:
    return ""

@bottle.get('/signup')
def signup():
  return '''
    <form action="/signup" method="post">
      Username: <input name="username" type="text" />
      Password: <input name="password" type="password" />
      Email: <input name="email" type="text" />
      <input value="Signup" type="submit" />
    </form>
  '''

@bottle.post('/signup')
def post_signup():
  email = bottle.request.forms.get("email")
  username = bottle.request.forms.get("username")
  password = bottle.request.forms.get("password")

  if not users.add_user(username, password, email):
    return "username is already in use"
  else:
    return username

@bottle.get('/addCollection')
def get_add_collection():
  return '''
    <form action="/addCollection" method="post">
      Title: <input name="title" type="text" />
      Description: <input name="description" type="text" />
      Category: <input name="category" type="text" />
      Username: <input name="username" type="text" />
      Picture: <input name="picture" type="text" />
      <input value="Create Collection" type="submit" />
    </form>
  '''

@bottle.post('/addCollection')
def post_add_collection():
  title = bottle.request.forms.get("title")
  description = bottle.request.forms.get("description")
  category = bottle.request.forms.get("category")
  picture = bottle.request.forms.get("picture")
  username = bottle.request.forms.get("username")

  return collections.insert_collection(title, description, category, picture, 'false', username)

@bottle.get('/editCollection')
def get_edit_collection():
  return '''
    <form action="/editCollection" method="post">
      Collection Id: <input name="col_id" type="text" />
      Title: <input name="title" type="text" />
      Description: <input name="description" type="text" />
      Category: <input name="category" type="text" />
      Picture: <input name="picture" type="text" />
      <input value="Edit Collection" type="submit" />
    </form>
  '''

@bottle.post('/editCollection')
def post_edit_collection():
  col_id = bottle.request.forms.get("col_id")
  title = bottle.request.forms.get("title")
  description = bottle.request.forms.get("description")
  category = bottle.request.forms.get("category")
  picture = bottle.request.forms.get("picture")

  collections.edit_collection(col_id, title, description, category, picture)

  return 'Changes Saved!'

@bottle.get('/removeCollection')
def get_remove_collection():
  return '''
    <form action="/removeCollection" method="post">
      Collection Id: <input name="col_id" type="text" />
      <input value="Remove Collection" type="submit" />
    </form>
  '''

@bottle.post('/removeCollection')
def post_remove_collection():
  col_id = bottle.request.forms.get("col_id")
  try:
    items.remove_items_from_collection(col_id)
    collections.remove_collection(col_id)
  except:
    print sys.exc_info()[0]
    return "Error Occurred"

  return col_id

@bottle.get('/addItem')
def get_add_item():
  return '''
    <form action="/addItem" method="post">
      Title: <input name="title" type="text" />
      Description: <input name="description" type="text" />
      Picture: <input name="picture" type="text" />
      Collection Id: <input name="col_id" type="text" />
      <input value="Add Item to Collection" type="submit" />
    </form>
  '''

@bottle.post('/addItem')
def post_add_item():
  title = bottle.request.forms.get("title")
  description = bottle.request.forms.get("description")
  picture = bottle.request.forms.get("picture")
  col_id = bottle.request.forms.get("col_id")

  return items.insert_item(title, description, picture, col_id)

@bottle.get('/getItems')
def get_get_items():
  return '''
    <form action="/getItems" method="post">
      Collection Id: <input name="col_id" type="text" />
      <input value="Get Items from Collection" type="submit" />
    </form>
  '''

@bottle.post('/getItems')
def post_get_items():
  col_id = bottle.request.forms.get("col_id")
  return str(items.get_items(col_id))

@bottle.get('/removeItem')
def get_remove_items():
  return '''
    <form action="/removeItem" method="post">
      Item Id: <input name="item_id" type="text" />
      <input value="Remove Item" type="submit" />
    </form>
  '''

@bottle.post('/removeItem')
def post_remove_item():
  item_id = bottle.request.forms.get("item_id")
  return str(items.remove_item(item_id))

@bottle.get('/editUser')
def get_edit_user():
  return '''
    <form action="/editUser" method="post">
      Username: <input name="username" type="text" />
      Display Name: <input name="display_name" type="text" />
      Email: <input name="email" type="text" />
      Picture: <input name="picture" type="text" />
      <input value="Edit User" type="submit" />
    </form>
  '''

@bottle.post('/editUser')
def post_edit_user():
  username = bottle.request.forms.get("username")
  display_name = bottle.request.forms.get("display_name")
  email = bottle.request.forms.get("email")
  picture = bottle.request.forms.get("picture")

  users.edit_user(username, display_name, email, picture)

  return 'Changes Saved!'

connection = pymongo.MongoClient()
database = connection.collector

users = userDAO.UserDAO(database)
collections = collectionDAO.CollectionDAO(database)
items = itemDAO.ItemDAO(database)

bottle.debug(True)
bottle.run(host='0.0.0.0', port=8080)
