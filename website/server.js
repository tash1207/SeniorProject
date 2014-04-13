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
	title: String,
	description: String,
	category: String,
	picture: String,
	owner: String,
	is_private: Boolean,
	favorites: Number,
	}
);

var User = mongoose.model('User', {
	email: String,
	password: String,
	display_name: String,
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
	app.get('/item/:collectionId', function(request,response) {
		Item.find({ collection_id: request.params.collectionId}).exec(function(error, items) {
			if (error) {
				response.send(error);
			}
			response.json(items);
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
	//get single collection of items
	app.get('/collection/:collectionId', function(request,response) {
		Collection.findById(request.params.collectionId, function(error, Id) {
			if (error) {
				response.send(error);
			}
			
			response.json(Id);
		});
	});
		
	//collection stuff
	//get all
	app.get('/collection', function(request,response) {
		Collection.find(function(error, collections) {
			//if error, then send error message
			if(error) {
				response.send(error);
			}
			//return Items
			response.json(collections);
			});
		});
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
			title: request.body.title,
			description: request.body.description,
			category: request.body.category,
			picture: request.body.picture,
			owner: request.body.owner,
			is_private: request.body.is_private,
			favorites: request.body.favorites
		}, function (error, collection) {
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
	//update a collection	
	app.post('/collection/:collection_id', function(request,response) {
		Collection.findByIdAndUpdate(request.params.collection_id, {
			category: request.body.catgeory,
			title: request.body.title,
			description: request.body.description,
			picture: request.body.picture,
			owner: request.body.owner,
			is_private: request.body.is_private,
			favorites: request.body.favorites
			}, function(error, collection) {
				if (error) {
					response.send(error);
				}
				response.json(collection);
			});
		});

	//delete 
	app.delete('/collection/:collection_id', function(request, response) {
		Collection.remove({
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
	// get all users
	app.get('/user', function(request,response) {
		User.find(function(error, users) {
			if(error) {
				response.send(error);
			}
			
			response.json(users);
		});
	});
	//get a single user
	app.get('/user/:userId', function(request,response) {
		User.findById(request.params.userId).exec(function(error, userId) {
			if (error) {
				response.send(error);
			}
			response.json(userId);
			});
		});
		
	//create a user
	app.post('/user', function(request, response) {
		User.create( {
			email: request.body.email,
			password: request.body.password,
			display_name: request.body.display_name,
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
			email: request.body.email,
			password: request.body.password,
			display_name: request.body.display_name,
			picture: request.body.picture
			}, function(error, item) {
				if (error) {
					response.send(error);
				}
				response.json(user);
			});
		});
		
	//delete a user
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

//login
	app.get('/login', function(request,response) {
		User.findOne({
			display_name: request.params.display_name,
			password: request.params.password
		},function(error, user) {
			if (error) {
				response.send(error);
			}
			response.json(user);
		});
	});	
	//add comment routes

	//app.get('*', function(request, response) {
		//response.sendfile('./app/index.html');
	//});

exports = module.exports = app;
	
app.listen(8080);
console.log("App listening on port 8080");