(function() {
	'use strict';
	
	var moduleName = 'app.item.server',
	
		angularDependencies = [];
	
	define([
		'angular'
		], function(angular) {
		
			var module = angular.module(moduleName, angularDependencies);
			
			module.factory('itemServer', ['$http',
				function($http) {
					var itemServer = {};
					
					itemServer.getId = function(id) {
						id = id || '';
						return $http.get('api/item' + id);
					};
					
					itemServer.setId = function(id) {
						id = id || '';
						return $http.post('api/item' + id);
					}
					
					itemServer.getCollectionId = function(collectionId) {
						collectionId = collectionId || '';
						return $http.get('api/item' + id);
					}
					
					itemServer.setCollectionId = function(collectionId) {
						collectionId = collectionId || '';
						return $http.set('api/item' + id);
					}
					
					}
				]);
			
			return module;
			
			});
		
	
});