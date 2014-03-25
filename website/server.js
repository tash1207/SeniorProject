'use strict';

var express = require('express');
var mongoose = require('mongoose');
require('express-namespace');

var app = express();

mongoose.connect('mongodb://localhost/app');

//configure express start up
app.configure(function() {
	app.use(express.static(__dirname + '/app'));
	app.use(express.logger('dev'));
	app.use(express.bodyParser());
	app.use(express.methodOverride());
});

//model code
var Item = mongoose.model('item', {
	id: Number,
	collection_id: Number,
	title: String,
	description: String,
	picture: String
	}
);

var Comment = mongoose.model('comment', {
	userName: String,
	text: String
	}
);

var Collection = mongoose.model('collection', {
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

var User = mongoose.model('user', {
	id: Number,
	userName: String,
	email: String,
	passWord: String,
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
});

	//add user and comment routes

	app.get('*', function(request, response) {
		response.sendfile('./app/index.html');
	});

exports = module.exports = app;
	
app.listen(8080);
console.log("App listening on port 8080");