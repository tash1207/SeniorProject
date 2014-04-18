(function() {
	'use strict';
	
	var moduleName = 'app.user.userServer',
	
		angularDependencies = [];
	
	define([
		'angular'
		], function(angular) {
		
			var module = angular.module(moduleName, angularDependencies);
			
			module.factory('userServer', ['$http',
				function($http) {
					var userServer = {};
					
					userServer.get = function(id) {
						id = id || '';
						return $http.get('/api/user/' + id);
					};
					
					userServer.create = function(user) {
						return $http.post('/api/user/', user);
					};
					
					userServer.update = function(user) {
						return $http.post('/api/user/' + user._id, user);
					};
					
					userServer.delete = function(id) {
						return $http.delete('/api/user/' + id);
					};
					
					userServer.logout = function(user) {
						return $http.post('/api/logout/', user);
					};
					
					return userServer;
				
					
					
					}
				]);
			
			return module;
			
			});
		
	
})();