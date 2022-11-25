angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.loadCart = function () {
        if ($scope.isUserLoggedIn()) {
            $http({
                    url: contextPath + '/cart',
                    method: 'GET',
                    params: {
                        userName: $localStorage.webMarketUser.username
                    }
                }
            ).then(function (response) {
                $scope.cartList = response.data;
                $scope.total = 0;
                for (let i = 0; i < $scope.cartList.length; i++) {
                    console.log($scope.cartList[i].cost);
                    $scope.cartList[i].cost = $scope.cartList[i].price * $scope.cartList[i].count;
                    $scope.total += $scope.cartList[i].cost;
                }
            }).catch(function (err){
                alert("Пожалуйста, авторизуйтесь");
                $scope.clearUser();
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


    $scope.addToCart = function (productId) {
        // document.getElementById("error_text").style.visibility = 'hidden';

        if ($scope.isUserLoggedIn()) {

            $http({
                url: contextPath + '/cart',
                method: 'POST',
                params: {
                    productId: productId
                }

            }).then(function () {
                $scope.loadCart();
            });
        } else {
            alert("Пожалуйста, авторизуйтесь!");
        }

    };


    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
        window.location.href = 'http://' + window.location.hostname + ':' + window.location.port + '/market';
    };

    $scope.loadCart();
});