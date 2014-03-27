'use strict';

var express = require('express');
var mongoose = require('mongoose');
require('express-namespace');
var path = require('path');
var	fs = require('fs');

var app = express();

mongoose.connect('mongodb://localhost/app');

//configure express start up
app.configure(function() {
	app.use(express.static(__dirname + '/app'));
	app.use(express.logger('dev'));
	app.use(express.bodyParser());
	app.use(express.multipart());
	app.use(express.methodOverride());
});

//model code
var Item = mongoose.model('Item', {
	id: Number,
	collection_id: String,
	title: String,
	description: String,
	picture: String
	}
);

var Comment = mongoose.model('Comment', {
	userName: String,
	text: String
	}
);

var Collection = mongoose.model('Collection', {
	id: Number,
	title: String,
	description: String,
	category: String,
	picture: String,
	owner: String,
	isPrivate: Boolean,
	favorites: Number,
	}
);

var User = mongoose.model('User', {
	id: Number,
	userName: String,
	email: String,
	password: String,
	displayName: String,
	picture: String
	}
);

//api code
app.namespace('/api', function() {

	//get all Items
	app.get('/item', function(request,response) {
		Item.find(function(error, items) {
			//if error, then send error message
			if(error) {
				response.send(error);
			}
			//return Items
			response.json(items);
			});
		});

	//get one item
	app.get('/item/:item_id', function(request,response) {
		Item.findById(request.params.item_id).exec(function(error, item) {
			if (error) {
				response.send(error);
			}
			response.json(item);
			});
		});
		
	//image upload
	/*
	app.post('/upload', function(req, res) {
		console.log(req.files.uploadFile);
		res.send('sweet!');
		
		var tempPath = request.files.uploadFile.path;
		var newPath = './public/images/' + req.files.uploadFile.name;
		fs.rename(tempPath, newPath, function(err) {
			if (err) throw err;
			
			fs.unlink(tempPath, function() {
				if (err) throw err;
				response.send('File uploaded');
			});
		});
			
			
	}); */
		
	//create an item
	app.post('/item', function(request, response) {
		Item.create( {
			id: request.body.id,
			collection_id: request.body.collection_id,
			title: request.body.title,
			description: request.body.description,
			picture: request.body.picture
			}, function(error, item) {
				if (error) {
					response.send(error);
				}
				
				Item.findById(item._id).exec(function(error, item) {
					if (error) {
						response.send(error);
					}
					response.json(item);
					});
				});
				
			});
			
	//update an item		
	app.post('/item/:item_id', function(request,response) {
		Item.findByIdAndUpdate(request.params.item_id, {
			id: request.body.id,
			collection_id: request.body.collection_id,
			title: request.body.title,
			description: request.body.description,
			picture: request.body.picture
			}, function(error, item) {
				if (error) {
					response.send(error);
				}
				response.json(item);
			});
		});
		
	//delete an item
	app.delete('/item/:item_id', function(request,response) {
		Item.remove ( {
			_id : request.params.item_id
			}, function (error, item) {
				if (error) {
					response.send(error);
				}
				response.json( {
					'status': 'success'
				});
			});
		});
		
	//collection stuff
	//single
	app.get('/collection/:collection_id', function(request, response) {
		Collection.findById(request.params.collection_id).exec(function(error, collection) {
			if (error) {
				response.send(error);
			}
			response.json(collection);
		});
	});	

	//create
	app.post('/collection', function(request, response) {
		Collection.create ({
			id: request.body.id,
			title: request.body.title,
			description: request.body.description,
			category: request.body.category,
			picture: request.body.picture,
			owner: request.body.owner,
			isPrivate: request.body.isPrivate,
			favorites: request.body.favorites,
			item: request.body.item
		}, function (error, Collection) {
			if (error) {
				response.send(error);
			}
			
			Collection.findById(collection._id).exec(function(error, collection) {
				if (error) {
					response.send(error);
				}
				
				response.json(collection);
			});
		});
	});

	//delete 
	app.delete('/collection/:collection_id', function(request, response) {
		Item.remove({
			_id: request.params.collection_id
		}, function(error, collection) {
			if (error) {
				response.send(error);
			}
			response.json({
				'status': 'success'
			});
		});
	});
	// user stuff
	app.get('/user/:user_id', function(request,response) {
		User.findById(request.params.user_id).exec(function(error, user) {
			if (error) {
				response.send(error);
			}
			response.json(user);
			});
		});
		
	//create a user
	app.post('/user', function(request, response) {
		User.create( {
			id: request.body.id,
			userName: request.body.userName,
			email: request.body.email,
			password: request.body.password,
			displayName: request.body.displayName,
			picture: request.body.picture
			}, function(error, user) {
				if (error) {
					response.send(error);
				}
				
				Item.findById(user._id).exec(function(error, user) {
					if (error) {
						response.send(error);
					}
					response.json(user);
					});
				});
			});
			
	//update a user		
	app.post('/user/:user_id', function(request,response) {
		User.findByIdAndUpdate(request.params.user_id, {
			id: request.body.id,
			userName: request.body.userName,
			email: request.body.email,
			password: request.body.password,
			displayName: request.body.displayName,
			picture: request.body.picture
			}, function(error, item) {
				if (error) {
					response.send(error);
				}
				response.json(user);
			});
		});
		
	//delete an item
	app.delete('/user/:user_id', function(request,response) {
		User.remove ( {
			_id : request.params.user_id
			}, function (error, user) {
				if (error) {
					response.send(error);
				}
				response.json( {
					'status': 'success'
				});
			});
		});
});
	//add comment routes

	app.get('*', function(request, response) {
		response.sendfile('./app/index.html');
	});

exports = module.exports = app;
	
app.listen(8080);
console.log("App listening on port 8080");