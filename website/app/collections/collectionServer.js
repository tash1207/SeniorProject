(function() {
	'use strict';
	
	var moduleName = 'app.collection.collectionServer',
	
		angularDependencies = [];
	
	define([
		'angular'
		], function(angular) {
		
			var module = angular.module(moduleName, angularDependencies);
			
			module.factory('collectionServer', ['$http',
				function($http) {
					var collectionServer = {};
					
					collectionServer.get = function(id) {
						return $http.get('/api/collection/' + id);
					};
					
					collectionServer.create = function(collection) {
						return $http.post('/api/collection/', collection);
					};
					
					collectionServer.update = function(collection) {
						return $http.post('/api/collection/' + collection._id, collection);
					};
					
					collectionServer.delete = function(id) {
						return $http.delete('/api/collection/' + id);
					};
					
					return collectionServer;
				
					
					
					}
				]);
			
			return module;
			
			});
		
	
})();