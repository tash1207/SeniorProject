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
						url: '/collections/items/',
						templateUrl: '/items/items.html',
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
					$scope.item = item.data;
					
					}
				]);
			return module;
		});
	
	
})();