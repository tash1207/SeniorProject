(function() {
	'use strict';
	
	var moduleName = 'app.guestitem.guestitemServer',
	
		angularDependencies = [];
	
	define([
		'angular'
		], function(angular) {
		
			var module = angular.module(moduleName, angularDependencies);
			
			module.factory('guestitemServer', ['$http',
				function($http) {
					var guestitemServer = {};
					
					guestitemServer.get = function(id) {
						id = id || '';
						return $http.get('/api/guestitem/' + id);
					};
					
					guestitemServer.create = function(item) {
						return $http.post('/api/guestitem/', item);
					};
					
					guestitemServer.update = function(item) {
						return $http.post('/api/guestitem/' + item._id, item);
					};
					
					guestitemServer.delete = function(id) {
						return $http.delete('/api/guestitem/' + id);
					};
					/*
					itemServer.addPic = function(uploadFile) {
						return $http.post('/api/upload/', uploadFile);
					};*/
					
					return guestitemServer;
				
					
					
					}
				]);
			
			return module;
			
			});
		
	
})();