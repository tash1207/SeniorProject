(function() {
	'use strict';
	
	var moduleName = 'app.guest',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.guest.guestServer'
		// fill in dependencies later
		];
	
	define([
		'angular',
		'lodash',
		'ui.router',
		'ui.bootstrap',
		'app.guest.guestServer'
		// fill in as they come
		], function(angular) {
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.guest', {
						controller: 'guestController',
						url: '/guest/',
						templateUrl: 'guest/guest.html',
						resolve: {
							'guest': ['guestServer',
								function(guestServer) {
									return guestServer.get();
								}
							]
							
						}
					});
				}
			]);
			
			module.controller('guestController', ['$scope','$state','guestServer','guest',
				function($scope,$state,guestServer,guest) {
					console.log('guestController', guest);
				
					
					//add functionality here
					$scope.guest = guest.data;
					
					
					
					
					
					
					
					}
				]);
			return module;
		});
	
	
})();