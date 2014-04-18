(function() {
	'use strict';
	
	var moduleName = 'app.login.new',
	
		angularDependencies = [
			'ui.router',
			'app.login',
			'app.login.loginServer'
			// fill in
			];
	
	define([
		'angular',
		'ui.router',
		'app.login',
		'app.login.loginServer'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.login.new', {
						controller: 'loginNewController',
						url: '/newLogin',
						templateUrl: '/login/login_form.html',
					});
				}
			]);
			
			module.controller('loginNewController', ['$scope', '$state', 'loginServer',
				function($scope,$state,loginServer) {
					console.log('loginNewController');
					
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
					
					$scope.login = {
						email: null,
						password: null,
						display_name: null,
						picture: null
					};
					
					$scope.saveUser = function() {
						console.log('adding user...');
						loginServer.create($scope.login).then(
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