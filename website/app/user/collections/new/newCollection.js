(function() {
	'use strict';
	
	var moduleName = 'app.collection.new',
	
		angularDependencies = [
			'ui.router',
			'ui.bootstrap',
			'app.collection.collectionServer',
			// fill in
			];
	
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.collection.collectionServer'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.collection.new', {
						controller: 'collectionNewController',
						url: 'newCollection',
						templateUrl: 'user/collections/collection_form.html',
						
					});
				}
			]);
			
			module.controller('collectionNewController', ['$scope', '$state', 'collectionServer',
				function($scope,$state,collectionServer) {
					console.log('collectionNewController');
					
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
					
					$scope.collection = {
						title: null,
						description: null,
						category: null,
						picture: null,
						isPrivate: false,
						favorites: null
					};
					
					$scope.saveCollection = function() {
						console.log('adding collection...');
						collectionServer.create($scope.collection).then (
							function success(response) {
								reset();
								console.log(response);
								$scope.setFeedback(true, 'success', 'Success!');
							},
							
							function error(response) {
								console.log(response);
								$scope.setFeedback(true, 'danger', response);
							}
						)};
						
					$scope.setFeedback = function(hasFeedback, status, message) {
						$scope.feedback.hadFeedback = hasFeedback;
						$scope.feedback.status = status;
						$scope.feedback.message = message;
					};
					
				
				}
			]);
			
			return module;
		});
	
	
})();