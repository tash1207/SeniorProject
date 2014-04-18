(function() {
	'use strict';
	
	var moduleName = 'app.item.new',
	
		angularDependencies = [
			'ui.router',
			'angularFileUpload',
			'app.item',
			'app.item.itemServer'
			// fill in
			];
	
	define([
		'angular',
		'ui.router',
		'angular-file-upload',
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
			
			module.controller('itemNewController', ['$scope', '$state', 'collectionId', 'itemServer', '$fileUploader',
				function($scope,$state,collectionId,itemServer,$fileUploader) {
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
					
								//////////////
								// Creates a uploader
					var uploader = $scope.uploader = $fileUploader.create({
						scope: $scope,
						url: '/api/item/upload'
					});


					// ADDING FILTERS

					// Images only
					uploader.filters.push(function(item /*{File|HTMLInputElement}*/) {
						var type = uploader.isHTML5 ? item.type : '/' + item.value.slice(item.value.lastIndexOf('.') + 1);
						type = '|' + type.toLowerCase().slice(type.lastIndexOf('/') + 1) + '|';
						return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
					});


					// REGISTER HANDLERS

					uploader.bind('afteraddingfile', function (event, item) {
						console.info('After adding a file', item);
					});

					uploader.bind('whenaddingfilefailed', function (event, item) {
						console.info('When adding a file failed', item);
					});

					uploader.bind('afteraddingall', function (event, items) {
						console.info('After adding all files', items);
					});

					uploader.bind('beforeupload', function (event, item) {
						console.info('Before upload', item);
					});

					uploader.bind('progress', function (event, item, progress) {
						console.info('Progress: ' + progress, item);
					});

					uploader.bind('success', function (event, xhr, item, response) {
						console.info('Success', xhr, item, response);
					});

					uploader.bind('cancel', function (event, xhr, item) {
						console.info('Cancel', xhr, item);
					});

					uploader.bind('error', function (event, xhr, item, response) {
						console.info('Error', xhr, item, response);
					});

					uploader.bind('complete', function (event, xhr, item, response) {
						console.info('Complete', xhr, item, response);
					});

					uploader.bind('progressall', function (event, progress) {
						console.info('Total progress: ' + progress);
					});

					uploader.bind('completeall', function (event, items) {
						console.info('Complete all', items);
					});
					
				}
			]);
			module.directive('ngThumb', ['$window', function($window) {
				var helper = {
					support: !!($window.FileReader && $window.CanvasRenderingContext2D),
					isFile: function(item) {
						return angular.isObject(item) && item instanceof $window.File;
					},
					isImage: function(file) {
						var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
						return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
					}
				};

				return {
					restrict: 'A',
					template: '<canvas/>',
					link: function(scope, element, attributes) {
						if (!helper.support) return;

						var params = scope.$eval(attributes.ngThumb);

						if (!helper.isFile(params.file)) return;
						if (!helper.isImage(params.file)) return;

						var canvas = element.find('canvas');
						var reader = new FileReader();

						reader.onload = onLoadFile;
						reader.readAsDataURL(params.file);

						function onLoadFile(event) {
							var img = new Image();
							img.onload = onLoadImage;
							img.src = event.target.result;
						}

						function onLoadImage() {
							var width = params.width || this.width / this.height * params.height;
							var height = params.height || this.height / this.width * params.width;
							canvas.attr({ width: width, height: height });
							canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
						}
					}
				};
			}]);
			
			return module;
		});
	
	
})();