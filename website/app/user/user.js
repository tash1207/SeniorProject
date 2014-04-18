(function() {
	'use strict';
	
	var moduleName = 'app.user',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.user.new',
		'app.user.userServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.user.new',
		'app.user.userServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.user', {
						controller: 'userController',
						url: '/user/',
						templateUrl: 'user/user.html',
						resolve: {
							'user': ['userServer',
								function(userServer) {
									return userServer.get();
								}
							]
							
						}
					});
				}
			]);
			
			module.controller('userController', ['$scope','$state','userServer','user',
				function($scope,$state,userServer,user) {
					console.log('userController', user);
					
					//add functionality here
					$scope.user = user.data;
					
					$scope.logout = function() {
						userServer.logout($scope.user).then(function(response) {
							console.log(response);
							$state.go('app.login');
						});
					};
					
					
					$scope.deleteUser = function(id) {
						var index = _.findIndex($scope.user, {
							'_id': id
						});
						
						userServer.delete(id).then(function(response) {
							console.log(response);
							$scope.user.splice(index,1);
						});
					};
					
					
					
					}
				]);
			return module;
		});
	
	
})();