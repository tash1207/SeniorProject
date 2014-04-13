(function() {
	'use strict';
	
	var moduleName = 'app.item',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.item.new',
		'app.item.itemServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.item.new',
		'app.item.itemServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.item', {
						controller: 'itemController',
						url: '/:userId/collections/:collectionId/items/',
						templateUrl: 'user/collections/items/item.html',
						resolve: {
							'item': ['itemServer',
								function(itemServer) {
									return itemServer.get();
								}
							],
							'items': ['itemServer', '$stateParams',
								function(itemServer, $stateParams) {
									return itemServer.get($stateParams.collectionId).then(function(response) {
										return response.data;
									});
								}
							],
							'collectionId': ['$stateParams', 'collectionServer',
								function($stateParams, collectionServer) {
									return collectionServer.get($stateParams.collectionId).then(function(response) {
										return response.data;
									});
								}
							]
						}
					});
				}
			]);
			
			module.controller('itemController', ['$scope','$state','itemServer','item','collectionId','items',
				function($scope,$state,itemServer,item,collectionId,items) {
					console.log('itemController', item);
					console.log(collectionId);
					console.log(items);
					
					//add functionality here
					$scope.item = item.data;
					$scope.collectionId = collectionId._id;
					console.log(collectionId._id);
					
					
					
					
					$scope.deleteItem = function(id) {
						var index = _.findIndex($scope.item, {
							'_id': id
						});
						
						itemServer.delete(id).then(function(response) {
							console.log(response);
							$scope.item.splice(index,1);
						});
					};
					
					$scope.filterId = function(item) {
						return (item.collection_id == $scope.collectionId);
					}
					
					
					
					}
				]);
			return module;
		});
	
	
})();