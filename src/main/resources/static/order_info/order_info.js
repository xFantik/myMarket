angular.module('market-front').controller('orderInfoController', function ($scope, $http, $location, $routeParams, $localStorage) {
    const contextPath = 'http://' + window.location.hostname + ':' + window.location.port + '/market/';
    $scope.orderId=$routeParams.orderId;
    $scope.loadOrder = function () {
        if ($scope.isUserLoggedIn()) {
            $http.get(contextPath + 'api/v1/order/' + $routeParams.orderId)

            .then(function (response) {
                $scope.order = response.data;
                $scope.total = 0;
                for (let i = 0; i < $scope.order.length; i++) {
                    $scope.order[i].cost = $scope.order[i].price * $scope.order[i].count;
                    $scope.total += $scope.order[i].cost;
                }



            }).catch(function (err) {
                console.log(err);
                if (err.status===401) {
                    alert("Пожалуйста, авторизуйтесь");
                    $scope.clearUser();
                } else alert(err.data.message)
            });
        } else $scope.cartList = null;
    };



    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
        window.location.href = 'http://' + window.location.hostname + ':' + window.location.port + '/market';
    };




    $scope.loadOrder();
});