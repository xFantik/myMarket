angular.module('market-front').controller('editProductController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/market/';

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
            alert("Пожалуйста, авторизуйтесь");
            $scope.clearUser();
        });
    }

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
        window.location.href = 'http://' + window.location.hostname + ':' + window.location.port + '/market';
    };
    $scope.prepareProductForUpdate();
});