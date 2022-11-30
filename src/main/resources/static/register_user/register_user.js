angular.module('market-front').controller('registerUserController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://' + window.location.hostname + ':' + window.location.port + '/market/';


    $scope.registerUser = function () {
        if (!$scope.new_user.username) {
            alert('Введите имя пользователя')

        } else if ($scope.new_user.password !== $scope.new_user.password2) {
            alert('Пароли не совпадают')

        } else if (!String($scope.new_user.email)
            .toLowerCase()
            .match(
                /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            )) {
            alert('Неверный email')

        } else {
            $http.post(contextPath + 'registration', $scope.new_user)
                .then(function successCallback(response) {
                    $scope.new_user = null;
                    alert('Пользователь создан');
                    $location.path('/store');
                }, function failureCallback(response) {
                    alert(response.data.message);
                });
        }
    }

});