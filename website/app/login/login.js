(function() {
	'use strict';
	
	var moduleName = 'app.login',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.login.loginServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.login.loginServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.login', {
						controller: 'loginController',
						url: '/login',
						templateUrl: '/login/login.html',
						resolve: {
							
						}
					});
				}
			]);
			
			module.controller('loginController', ['$scope','$state','loginServer',
				function($scope,$state,loginServer) {
					console.log('loginController');
					
					$scope.feedback = {
						hasFeedback: false,
						message: null,
						status: null
					};
					
					$scope.setFeedback = function(hasFeedback, status, message) {
						$scope.feedback.hadFeedback = hasFeedback;
						$scope.feedback.status = status;
						$scope.feedback.message = message;
					};
					
					$scope.user = {
						display_name: null,
						password: null,
					};
					
					
					$scope.login = function() {
						console.log('logging in...');
					
						loginServer.get($scope.user).then(function success(response) {
								console.log(response);
								$scope.setFeedback(true, 'success', 'Success!');
							},
							
							function error(response) {
								console.log(response);
								$scope.setFeedback(true, 'danger', response);
							}
						)};
					
				
					
					}
				]);
			return module;
		});
	
	
})();