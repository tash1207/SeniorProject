import pymongo
import userDAO
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
def do_login():
  username = bottle.request.forms.get('username')
  password = bottle.request.forms.get('password')

  user_record = users.validate_login(username, password)

  if user_record:
    return user_record

  else:
    return "<p>Login failed.</p>"

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
def do_signup():
  email = bottle.request.forms.get("email")
  username = bottle.request.forms.get("username")
  password = bottle.request.forms.get("password")

  if not users.add_user(username, password, email):
    return "username is already in use"
  else:
    return username


connection = pymongo.MongoClient()
database = connection.collector

users = userDAO.UserDAO(database)

bottle.debug(True)
bottle.run(host='0.0.0.0', port=8080)
