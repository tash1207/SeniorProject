# Mongo + PyMongo + Bottle


This is the code for the backend of the application which utilizes MongoDB, a non-relational database. The application interacts with MongoDB through the PyMongo driver. The API routes were written using the Bottle framework.

# API Documentation

## /login

The user logs into their account.

### URL

http://tashawych.co:8080/login

### HTTP method

POST

### Parameters

+ username
+ password

### Response

If successful, return user as JSONObject:

+ _id
+ display_name
+ email
+ picture

If not:

+ empty string

## /signup

The user signs up for an account.

### URL

http://tashawych.co:8080/signup

### HTTP method

POST

### Parameters

+ username
+ password
+ email (optional)

### Response

If successful:

+ username

If not:

+ error message

## /addCollection

Creates a new collection.

### URL

http://tashawych.co:8080/addCollection

### HTTP method

POST

### Parameters

+ title
+ description
+ category
+ picture (optional)
+ username

### Response

If successful:

+ collection_id

If not:

+ empty string

## /editCollection

Edits a collection's title, description, category, and picture.

### URL

http://tashawych.co:8080/editCollection

### HTTP method

POST

### Parameters

+ col_id
+ title
+ description
+ category
+ picture (optional)

### Response

If successful:

+ "Changes Saved!"

If not:

+ empty string

## /addItem

Creates a new item within a collection.

### URL

http://tashawych.co:8080/addItem

### HTTP method

POST

### Parameters

+ title (optional)
+ description (optional)
+ picture
+ col_id

### Response

If successful:

+ item_id

If not:

+ empty string

## /getItems

Gets all items from a collection.

### URL

http://tashawych.co:8080/getItems

### HTTP method

POST

### Parameters

+ col_id

### Response

If successful, return array of items as JSONObjects:

+ _id
+ title
+ description
+ picture
+ collection_id

If not:

+ empty array []

## /editUser

Edits a user's display name, email, and picture.

### URL

http://tashawych.co:8080/editUser

### HTTP method

POST

### Parameters

+ username
+ display_name
+ email
+ picture (optional)

### Response

If successful:

+ "Changes Saved!"

If not:

+ empty string