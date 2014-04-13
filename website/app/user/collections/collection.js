(function() {
	'use strict';
	
	var moduleName = 'app.collection',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.collection.new',
		'app.collection.collectionServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'lodash',
		'ui.router',
		'ui.bootstrap',
		'app.collection.new',
		'app.collection.collectionServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.collection', {
						controller: 'collectionController',
						url: '/:userId/collections/',
						templateUrl: 'user/collections/collection.html',
						resolve: {
							'collection': ['collectionServer',
								function(collectionServer) {
									return collectionServer.get();
								}
							],
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
			
			module.controller('collectionController', ['$scope','$state','collectionServer','collection','userId',
				function($scope,$state,collectionServer,collection,userId) {
					console.log('collectionController', collection);
					console.log(userId);
					
					//add functionality here
					$scope.collection = collection.data;
					
					
					$scope.userId = userId._id;
					
					$scope.deleteCollection = function(id) {
						var index = _.findIndex($scope.collection, {
							'_id': id
						});
						
						collectionServer.delete(id).then(function(response) {
							console.log(response);
							$scope.collection.splice(index,1);
						});
					};
					$scope.filterId = function(collection) {
						return (collection.owner == $scope.userId);
					}
					
					}
				]);
			return module;
		});
	
	
})();