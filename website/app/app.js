(function() {
	'use strict';
	
	var moduleName = 'app',
	
	angularDependencies = [
		'ui.router',
		'ui.bootstrap',
		'http-auth-interceptor',
		'app.item',
		'app.collection',
		'app.login',
		'app.create',
		'app.guest',
		'app.guestitem',
		'app.user'
		// fill in
		];
		
	define([
		'angular',
		'angular-http-auth',
		'ui.router',
		'ui.bootstrap',
		'app.item',
		'app.guest',
		'app.guestitem',
		'app.login.new',
		'app.collection',
		'app.create',
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
				controller: 'appCtrl',
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
	
	module.controller('appCtrl', ['$scope', '$state','authService',
		function($scope,$state,authService) {
			console.log('appCtrl');
			
			
			
			
		}
	]);
	
	module.run(['$rootScope', '$state', '$stateParams','authService',
		function($rootScope, $state, $stateParams,authService) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
			
			
			console.log(authService);
			$rootScope.$on('event:auth-loginRequired', function() {
				console.log('Log in required');
				$state.go('app.login');
			});
			
			
			$rootScope.$on('$routeChangeError', function () {
				console.log('failed to change routes', arguments);
			});
		}
	]);
	
	angular.bootstrap(document.querySelector('html'), [moduleName]);
	
	return module;
	});
})();
	
			 

		