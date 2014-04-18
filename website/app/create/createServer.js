(function() {
	'use strict';
	
	var moduleName = 'app.create.createServer',
	
		angularDependencies = [];
	
	define([
		'angular'
		], function(angular) {
		
			var module = angular.module(moduleName, angularDependencies);
			
			module.factory('createServer', ['$http','$q',
				function($http, $q) {
					var createServer = {};
					
					/*createServer.get = function(id) {
						id = id || '';
						return $http.get('/api/login/' + id);
					};*/
					
					createServer.login = function(user) {
							console.log('Hi');
							return $http.post('/api/login/', user);
					};
					
					createServer.create = function(login) {
						return $http.post('/api/user/', login);
					};
					
					createServer.update = function(login) {
						return $http.post('/api/login/' + login._id, login);
					};
					
					createServer.delete = function(id) {
						return $http.delete('/api/login/' + id);
					};
					/*
					itemServer.addPic = function(uploadFile) {
						return $http.post('/api/upload/', uploadFile);
					};*/
					
					return createServer;
				
					
					
					}
				]);
			
			return module;
			
			});
		
	
})();