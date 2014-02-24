from bottle import get, post, request, route, run, template

@route('/hello/<name>')
def index(name):
  return template('<b>Hello {{name}}</b>!', name=name)

@get('/login')
def login():
  return '''
    <form action="/login" method="post">
      Username: <input name="username" type="text" />
      Password: <input name="password" type="password" />
      <input value="Login" type="submit" />
    </form>
  '''

@post('/login')
def do_login():
  username = request.forms.get('username')
  password = request.forms.get('password')
  if (username == 'tash1207' and password != ''):
    return "<p>Your login information was correct.</p>"
  else:
    return "<p>Login failed.</p>"

run(host='0.0.0.0', port=8080)
