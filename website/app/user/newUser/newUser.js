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
						url: 'newUser',
						templateUrl: '/user/user_form.html'
					});
				}
			]);
			
			module.controller('userNewController', ['$scope', '$state', 'userServer',
				function($scope,$state,userServer) {
					console.log('userNewController');
					
					$scope.feedback = {
                    hasFeedback: false,
                    message: null,
                    status: null
					};
					
					function reset() {
						_.forEach($scope.item, function(item, index) {
							$scope.item[index] = null;
						});
					}
					
					$scope.user = {
						email: null,
						password: null,
						display_name: null,
						picture: null
					};
					
					$scope.saveUser = function() {
						console.log('adding user...');
						userServer.create($scope.user).then(
							function success(response) {
								reset();
								console.log(response);
								$scope.setFeedback(true, 'success', 'Success!');
							},
							
							function error(response) {
								console.log(response);
								$scope.setFeedback(true, 'danger', response);
							}
						
					);
					};
					$scope.setFeedback = function(hasFeedback, status, message) {
								$scope.feedback.hasFeedback = hasFeedback;
								$scope.feedback.status = status;
								$scope.feedback.message = message;
							};
					
				}
			]);
			
			return module;
		});
	
	
})();