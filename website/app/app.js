var collector = angular.module('app', []);

function mainController($scope, $http) {
	$scope.formData = {};
	
	$http.get('/api/item')
		.success(function(data) {	
			$scope.items = data;
			console.log(data);
		})
		.error(function(data) {
			console.log('Error: ' + data);
		});
		
		$scope.createItem = function() {
			$http.post('/api/item', $scope.formData)
				.success(function(data) {
					$scope.formData = {};
					$scope.items = data;
					console.log(data);
				})
				.error(function(data) {
					console.log('Error: ' + data);
				});
			};
			