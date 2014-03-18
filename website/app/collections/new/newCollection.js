(function() {
	'use strict';
	
	var moduleName = 'app/collections/new',
	
		angularDependencies = [
			
			'app/collections/collectionServer',
			// fill in
			];
	
	define([
		'angular',
		'app/collections/collectionServer'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app/collections/new', {
						controller: 'collectionNewController',
						url: '/app/collections/new',
						templateUrl: 'app/collections/collection_form.html'
					});
				}
			]);
			
			module.controller('itemNewController', ['$scope', '$state', 'collectionServer',
				function($scope,$state,collectionServer) {
					console.log('collectionServer');
					
					$scope.collection = {
						id: null,
						title: null,
						description: null,
						category: null,
						picture: null,
						owner: null,
						isPrivate: null,
						favorites: null,
						item: null
					};
					
					$scope.saveCollection = function() {
						console.log('adding collection...');
						collectionServer.create($scope.collection);
						
					
				}
			]);
			
			return module;
		});
	
	
});