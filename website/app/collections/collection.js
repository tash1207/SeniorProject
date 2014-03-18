(function() {
	'use strict';
	
	var moduleName = 'app/collection',
	
	angularDependencies = [
		'app/collection/new',
		'app/collectionServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'app/collection/new',
		'app/collectionServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app/collection', {
						controller: 'collectionController',
						url: '/collections/',
						templateUrl: 'collections/collections.html',
						resolve: {
							'collection': ['collectionServer',
								function(collectionServer) {
									collectionServer.get();
								}
							]
						}
					});
				}
			]);
			
			module.controller('collectionController', ['$scope','$state','collectionServer','collection',
				function($scope,$state,collectionServer,item) {
					console.log('collectionController', collection);
					
					//add functionality here
					
					}
				]);
			return module;
		});
	
	
})();