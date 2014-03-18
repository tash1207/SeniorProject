(function() {
	'use strict';
	
	var moduleName = 'app/collections/items',
	
	angularDependencies = [
		'app/collections/items/new',
		'app/collections/items/itemServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'app/collections/items/new',
		'app/collections/items/itemServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app/collections/items', {
						controller: 'itemController',
						url: '/collections/items/',
						templateUrl: 'collections/items/items.html',
						resolve: {
							'item': ['itemServer',
								function(itemServer) {
									itemServer.get();
								}
							]
						}
					});
				}
			]);
			
			module.controller('itemController', ['$scope','$state','itemServer','item',
				function($scope,$state,itemServer,item) {
					console.log('itemController', item);
					
					//add functionality here
					
					}
				]);
			return module;
		});
	
	
})();