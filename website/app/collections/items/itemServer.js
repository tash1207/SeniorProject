(function() {
	'use strict';
	
	var moduleName = 'app.item.itemServer',
	
		angularDependencies = [];
	
	define([
		'angular'
		], function(angular) {
		
			var module = angular.module(moduleName, angularDependencies);
			
			module.factory('itemServer', ['$http',
				function($http) {
					var itemServer = {};
					
					itemServer.get = function(id) {
						return $http.get('/api/item/' + id);
					};
					
					itemServer.create = function(item) {
						return $http.post('/api/item/', item);
					};
					
					itemServer.update = function(item) {
						return $http.post('/api/item/' + item._id, item);
					};
					
					itemServer.delete = function(id) {
						return $http.delete('/api/item/' + id);
					};
					
					return itemServer;
				
					
					
					}
				]);
			
			return module;
			
			});
		
	
});