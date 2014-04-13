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
						resolve: {
							'userId': ['$stateParams', 'userServer',
									function($stateParams, userServer) {
										return userServer.get($stateParams.userId).then(function(response) {
											return response.data;
										});
									}
								]
							}
					});
				}
			]);
			
			module.controller('collectionNewController', ['$scope', '$state', 'collectionServer','userId',
				function($scope,$state,collectionServer,userId) {
					console.log('collectionNewController');
					console.log(userId);
					
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
						owner: userId._id,
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