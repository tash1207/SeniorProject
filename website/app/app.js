(function() {
	'use strict';
	
	var moduleName = 'app',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'app.item',
		'app.collection',
		'app.collection',
		'app.item.new'
		// fill in
		];
		
	define([
		'angular',
		'ui.router',
		'ui.bootstrap',
		'app.item',
		'app.item.new',
		'app.collection'
		//fill in
	], function(angular) {
	
	var module = angular.module(moduleName, angularDependencies);
	
	module.config(['$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {
			 
			 $urlRouterProvider.otherwise('');
			 $urlRouterProvider.when('', '/item');
			 $urlRouterProvider.when('', '/collection');
			 
			 $stateProvider.state('app', {
				url: '',
				views: {
					'main': {
						templateUrl: '/main/_main.html'
					},
					'nav' : {
						templateUrl: '/nav/_nav.html'
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
	
			 

		