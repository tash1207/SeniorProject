import pymongo
import userDAO
import collectionDAO
import bottle

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
    return user_record['_id']

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


connection = pymongo.MongoClient()
database = connection.collector

users = userDAO.UserDAO(database)
collections = collectionDAO.CollectionDAO(database)

bottle.debug(True)
bottle.run(host='0.0.0.0', port=8080)
