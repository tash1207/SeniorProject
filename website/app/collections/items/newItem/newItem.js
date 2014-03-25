(function() {
	'use strict';
	
	var moduleName = 'app.item.new',
	
		angularDependencies = [
			'ui.router',
			'app.item.itemServer',
			'app.item'
			// fill in
			];
	
	define([
		'angular',
		'ui.router',
		'app.item.itemServer',
		'app.item'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.item.new', {
						controller: 'itemNewController',
						url: 'collections/items/newItem/',
						templateUrl: 'collections/items/item_form.html'
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
						
					
					};
				}
			]);
			
			return module;
		});
	
	
});