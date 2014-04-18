(function() {
	'use strict';
	
	var moduleName = 'app.guestitem',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.guestitem.guestitemServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.guestitem.guestitemServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.guestitem', {
						controller: 'guestitemController',
						url: '/guest/:collectionId/guestitems/',
						templateUrl: 'guest/guestitem/guestitem.html',
						resolve: {
							'guestitem': ['guestitemServer',
								function(guestitemServer) {
									return guestitemServer.get();
								}
							],
							'guestcollectionId': ['$stateParams', 'guestServer',
								function($stateParams, guestServer) {
									return guestServer.get($stateParams.collectionId).then(function(response) {
										return response.data;
									});
								}
							]
						}
					});
				}
			]);
			
			module.controller('guestitemController', ['$scope','$state','guestitemServer','guestitem','guestcollectionId',
				function($scope,$state,guestitemServer,guestitem,guestcollectionId) {
					console.log('guestitemController', guestitem);
					console.log(guestcollectionId);
					
					//add functionality here
					$scope.guestitem = guestitem.data;
					$scope.collectionId = guestcollectionId._id;
					console.log(guestcollectionId._id);
					
					
					
					$scope.filterId = function(guestitem) {
						return (guestitem.collection_id == $scope.collectionId);
					}
					
					
					
					}
				]);
			return module;
		});
	
	
})();