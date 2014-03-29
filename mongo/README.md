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

If successful:

+ username

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