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
						url: '/collections',
						templateUrl: 'collections/collections.html',
						resolve: {
							'collection': ['collectionServer',
								function(collectionServer) {
									return collectionServer.get();
								}
							]
						}
					});
				}
			]);
			
			module.controller('collectionController', ['$scope','$state','collectionServer','collection',
				function($scope,$state,collectionServer,collection) {
					console.log('collectionController', collection);
					
					//add functionality here
					
					}
				]);
			return module;
		});
	
	
})();