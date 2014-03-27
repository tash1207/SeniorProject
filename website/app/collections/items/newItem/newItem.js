(function() {
	'use strict';
	
	var moduleName = 'app.item.new',
	
		angularDependencies = [
			'ui.router',
			'app.item.itemServer'
			// fill in
			];
	
	define([
		'angular',
		'ui.router',
		'app.item.itemServer'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.item.new', {
						controller: 'itemNewController',
						url: 'newItem',
						templateUrl: 'collections/items/item_form.html'
					});
				}
			]);
			
			module.controller('itemNewController', ['$scope', '$state', 'itemServer',
				function($scope,$state,itemServer) {
					console.log('itemNewController');
					
					$scope.feedback = {
                    hasFeedback: false,
                    message: null,
                    status: null
					};
					
					$scope.item = {
						id: null,
						collection_id: null,
						title: null,
						description: null,
						picture: null,
						
					};
					
					$scope.saveItem = function() {
						console.log('adding item...');
						/*console.log(uploadFile);
						itemServer.addPic(uploadFile).then(
							function success(response) {
								console.log(response);
							},
							function error(response) {
								console.log(response);
							}
						);*/
						itemServer.create($scope.item).then(
							function success(response) {
								console.log(response);
									$scope.setFeedback(true, 'success', 'Success!');
							},
							
							function error(response) {
								console.log(response);
									$scope.setFeedback(true, 'danger', response);
							}
						);
					};
					$scope.setFeedback = function(hasFeedback, status, message) {
								$scope.feedback.hasFeedback = hasFeedback;
								$scope.feedback.status = status;
								$scope.feedback.message = message;
							};
					
				}
			]);
			
			return module;
		});
	
	
})();