(function() {
	'use strict';
	
	var moduleName = 'app.create',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.create.createServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.create.createServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.create', {
						controller: 'createController',
						url: '/create',
						templateUrl: '/create/login_form.html',
					});
				}
			]);
			
			module.controller('createController', ['$scope','$state','createServer',
				function($scope,$state,createServer) {
					console.log('createController');
					
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
					
					
					$scope.saveUser = function() {
						console.log('adding user...');
						createServer.create($scope.user).then(
							function success(response) {
								console.log(response);
								$scope.setFeedback(true, 'success', 'Success!');
							},
							
							function error(response) {
								console.log(response);
								$scope.setFeedback(true, 'danger', response);
							}
						
						);
					};
					
				
					
					}
				]);
			return module;
		});
	
	
})();