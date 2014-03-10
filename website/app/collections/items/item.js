(function() {
	'use strict';
	
	var moduleName = 'app.item',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.item.new',
		// fill in dependencies later
		];
	
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.item.new',
		'app.item.server'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$states.state('app.item', {
						controller: 'itemController',
						url: '/collections/items',
						templateUrl: 'collections/items/items.html',
						resolve: {
							'item': ['itemServer',
								function(itemServer) {
									itemServer.getId();
								}
							]
						}
					});
				}
			]);
			
			module.controller('itemController', ['$scope','$state','itemServer','item'
				function($scope,$state,itemServer,item) {
					console.log('itemController', item);
					
					
					
					}
				]);
			return module;
		});
	
	
})();