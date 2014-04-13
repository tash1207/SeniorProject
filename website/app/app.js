(function() {
	'use strict';
	
	var moduleName = 'app',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.item',
		'app.collection',
		'app.login',
		'app.user'
		// fill in
		];
		
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.item',
		'app.collection',
		'app.login',
		'app.user'
		//fill in
	], function(angular) {
	
	var module = angular.module(moduleName, angularDependencies);
	
	module.config(['$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {
			 
			 $urlRouterProvider.otherwise('');
			 //$urlRouterProvider.when('', '/collection');
			 
			 $stateProvider.state('app', {
				url: '',
				views: {
					
					'nav' : {
						templateUrl: 'nav/nav.html'
					},
					'main': {
						templateUrl: 'main/main.html'
					}
					
				}
			});
		}
	]);
	
	module.controller('appCtrl', ['$scope', '$state',
		function($scope, $state) {
			console.log('appCtrl');
			
		}
	]);
	
	module.run(['$rootScope', '$state', '$stateParams',
		function($rootScope, $state, $stateParams) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
			
			$rootScope.$on('$routeChangeError', function () {
				console.log('failed to change routes', arguments);
			});
		}
	]);
	
	angular.bootstrap(document.querySelector('html'), [moduleName]);
	
	return module;
	});
})();
	
			 

		