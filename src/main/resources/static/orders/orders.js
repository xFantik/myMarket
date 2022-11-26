angular.module('market-front').controller('orderController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://' + window.location.hostname + ':' + window.location.port + '/market/api/v1';

    $scope.loadOrders = function () {
        if ($scope.isUserLoggedIn()) {
            $http({
                    url: contextPath + '/order',
                    method: 'GET',
                    params: {
                        userName: $localStorage.webMarketUser.username
                    }
                }
            ).then(function (response) {
                $scope.orderList = response.data;
                $scope.hasOrders = $scope.orderList.length;
                for (let i = 0; i < $scope.orderList.length; i++) {
                    $scope.orderList[i].createdAt = $scope.orderList[i].createdAt.substring(0, 10);
                    console.log($scope.orderList[i].createdAt.substring(0, 10));
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
    //
    // $scope.generatePagesIndexes = function (startPage, endPage) {
    //     let arr = [];
    //     for (let i = startPage; i < endPage + 1; i++) {
    //         arr.push(i);
    //     }
    //     return arr;
    // }



    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
        window.location.href = 'http://' + window.location.hostname + ':' + window.location.port + '/market';
    };




    $scope.loadOrders();
});