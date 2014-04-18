(function() {
	'use strict';
	
	var moduleName = 'app.guest.guestServer',
	
		angularDependencies = [];
	
	define([
		'angular'
		], function(angular) {
		
			var module = angular.module(moduleName, angularDependencies);
			
			module.factory('guestServer', ['$http',
				function($http) {
					var guestServer = {};
					
					guestServer.get = function(id) {
						id = id || '';
						return $http.get('/api/guest/' + id);
					};
					
					guestServer.create = function(collection) {
						return $http.post('/api/guest/', collection);
					};
					
					guestServer.update = function(collection) {
						return $http.post('/api/guest/' + collection._id, collection);
					};
					
					guestServer.delete = function(id) {
						return $http.delete('/api/guest/' + id);
					};
					
					return guestServer;
				
					
					
					}
				]);
			
			return module;
			
			});
		
	
})();