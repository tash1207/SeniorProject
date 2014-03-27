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
						url: 'collections/new',
						templateUrl: 'collections/collection_form.html'
					});
				}
			]);
			
			module.controller('collectionNewController', ['$scope', '$state', 'collectionServer',
				function($scope,$state,collectionServer) {
					console.log('collectionNewController');
					
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
				}
			]);
			
			return module;
		});
	
	
})();