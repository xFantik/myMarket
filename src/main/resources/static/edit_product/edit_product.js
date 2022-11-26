angular.module('market-front').controller('editProductController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://' + window.location.hostname + ':' + window.location.port + '/market/';

    $scope.prepareProductForUpdate = function () {
        $http.get(contextPath + 'api/v1/products/' + $routeParams.productId)
            .then(function successCallback(response) {
                $scope.updated_product = response.data;
            }, function failureCallback(response) {
                console.log(response);
                alert(response.data.messages);
                $location.path('/store');
            });
    }

    $scope.updateProduct = function () {
        $http.put(contextPath + 'api/v1/products', $scope.updated_product)
            .then(function successCallback(response) {
                $scope.updated_product = null;
                alert('Продукт успешно обновлен');
                $location.path('/store');
            }, function failureCallback(response) {
                alert(response.data.messages);
            }).catch(function (err) {
            console.log(err);
            if (err.status===401) {
                alert("Пожалуйста, авторизуйтесь");
                $scope.clearUser();
            } else alert(err.data.message)
        });
    }

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
        window.location.href = 'http://' + window.location.hostname + ':' + window.location.port + '/market';
    };
    $scope.prepareProductForUpdate();
});