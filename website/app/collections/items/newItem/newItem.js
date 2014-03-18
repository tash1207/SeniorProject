(function() {
	'use strict';
	
	var moduleName = 'app/colections/items/new',
	
		angularDependencies = [
			
			'app/collections/items/itemServer',
			// fill in
			];
	
	define([
		'angular',
		'app/collections/items/itemServer'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app/collections/items/new', {
						controller: 'itemNewController',
						url: '/app/collections/items/new/',
						templateUrl: '/app/collections/items/item_form.html'
					});
				}
			]);
			
			module.controller('itemNewController', ['$scope', '$state', 'itemServer',
				function($scope,$state,itemServer) {
					console.log('itemServer');
					
					$scope.item = {
						id: null,
						collection_id: null,
						title: null,
						description: null,
						picture: null
					};
					
					$scope.saveItem = function() {
						console.log('adding item...');
						itemServer.create($scope.item);
						
					
				}
			]);
			
			return module;
		});
	
	
});