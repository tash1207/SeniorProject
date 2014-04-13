(function() {
	'use strict';
	
	var moduleName = 'app.item.new',
	
		angularDependencies = [
			'ui.router',
			'app.item',
			'app.item.itemServer'
			// fill in
			];
	
	define([
		'angular',
		'ui.router',
		'app.item',
		'app.item.itemServer'
		], function(angular) {
			
			var module = angular.module(moduleName, angularDependencies);
			
			module.config(['$stateProvider',
				function($stateProvider) {
					
					$stateProvider.state('app.item.new', {
						controller: 'itemNewController',
						url: 'newItem',
						templateUrl: 'user/collections/items/item_form.html',
						resolve: {
							'collectionId': ['$stateParams', 'collectionServer',
								function($stateParams, collectionServer) {
									return collectionServer.get($stateParams.collectionId).then(function(response) {
										return response.data;
									});
								}
							]
						}
						
					});
				}
			]);
			
			module.controller('itemNewController', ['$scope', '$state', 'collectionId', 'itemServer',
				function($scope,$state,collectionId,itemServer) {
					console.log('itemNewController');
					console.log('collectionServer', collectionId);
					
					$scope.feedback = {
                    hasFeedback: false,
                    message: null,
                    status: null
					};
					
					function reset() {
						_.forEach($scope.item, function(item, index) {
							$scope.item[index] = null;
						});
					}
					
					
					$scope.item = {
						collection_id: collectionId._id,
						title: null,
						description: null,
						picture: null
						
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
								reset();
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