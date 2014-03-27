(function() {
	'use strict';
	
	var moduleName = 'app.user.new',
	
		angularDependencies = [
			'ui.router',
			'app.user.userServer'
			// fill in
			];
	
	define([
		'angular',
		'ui.router',
		'app.user.userServer'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.user.new', {
						controller: 'userNewController',
						url: '/newUser',
						templateUrl: 'collections/user/user_form.html'
					});
				}
			]);
			
			module.controller('userNewController', ['$scope', '$state', 'userServer',
				function($scope,$state,userServer) {
					console.log('userNewController');
					
					$scope.user = {
						id: null,
						userName: null,
						email: null,
						password: null,
						displayName: null,
						picture: null
					};
					
					$scope.saveUser = function() {
						console.log('adding user...');
						userServer.create($scope.user).then(
							function success(response) {
								console.log(response);
							},
							
							function error(response) {
								console.log(response);
							}
						
					);
					};
					
				}
			]);
			
			return module;
		});
	
	
})();