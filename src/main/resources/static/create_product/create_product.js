angular.module('market-front').controller('createProductController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/';

    $scope.createProduct = function () {
        if ($scope.new_product == null) {
            alert('Форма не заполнена');
            return;
        }
        $http.post(contextPath + 'api/v1/products', $scope.new_product)
            .then(function successCallback (response) {
                $scope.new_product = null;
                alert('Продукт успешно создан');
                $location.path('/store');
            }, function failureCallback (response) {
                console.log(response);
                alert(response.data.messages);
            }).catch(function (err){
            alert("Пожалуйста, авторизуйтесь");
            $scope.clearUser();
        });
    }

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
        window.location.href = 'http://' + window.location.hostname + ':' + window.location.port + '/market';
    };
});