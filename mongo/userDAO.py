import pymongo
import hashlib

# The User Data Access Object handles all interaction with the User collection

class UserDAO:

  def __init__(self, db):
    self.db = db
    self.users = db.users

  def validate_login(self, username, password):

        user = None
        try:
                user = self.users.find_one({'_id': username})
        except:
                print "Unable to find user"

        if user is None:
                return None

        if user['password'] != self.make_pw_hash(password):
                print "Passwords do not match"
                return None

        return self.users.find_one({'_id': username}, {'display_name': 'true', 'email': 'true', 'picture': 'true'})

  def add_user(self, username, password, email):
        password_hash = self.make_pw_hash(password)
        user = {'_id': username, 'password': password_hash}
        if email != "":
                user['email'] = email

        try:
                self.users.insert(user, safe=True)
        except pymongo.errors.DuplicateKeyError:
                print "username has already been taken"
                return False

        return True

  def make_pw_hash(self, pw):
        return hashlib.sha256(pw).hexdigest()

  def edit_user(self, username, display_name, email, picture):
        self.users.update({'_id':username},{'$set':{'display_name':display_name, 'email':email}},upsert=False,multi=False)
        if picture != "":
            self.users.update({'_id':username},{'$set':{'picture':picture}},upsert=False,multi=False)

        return True

