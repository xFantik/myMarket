(function () {
    angular
        .module('market-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);


    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/edit_product/:productId', {
                templateUrl: 'edit_product/edit_product.html',
                controller: 'editProductController'
            })
            .when('/create_product', {
                templateUrl: 'create_product/create_product.html',
                controller: 'createProductController'
            }).when('/cart', {
            templateUrl: 'cart/cart.html',
            controller: 'cartController'
            })
            .when('/orders', {
            templateUrl: 'orders/orders.html',
            controller: 'orderController'
            }).when('/order_info/:orderId', {
            templateUrl: 'order_info/order_info.html',
            controller: 'orderInfoController'
            }).when('/register_user', {
                templateUrl: 'register_user/register_user.html',
                controller: 'registerUserController'
            })
            .otherwise({
                redirectTo: '/'
            });


    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.webMarketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webMarketUser.token;
        }
    }
})();

angular.module('market-front').controller('indexController', function ($rootScope, $scope, $http, $localStorage) {
    // const contextPath = 'http://localhost:8189/market';
    const contextPath = 'http://' + window.location.hostname + ':' + window.location.port + '/market'

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webMarketUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                        roles: response.data.roles
                    };

                    $scope.username = $localStorage.webMarketUser.username
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };


    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        window.location.href = 'http://' + window.location.hostname + ':' + window.location.port + '/market';
    };

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.webMarketUser) {
            $scope.username = $localStorage.webMarketUser.username
            return true;
        } else {
            return false;
        }
    };
    $rootScope.hasUserRole = function (role) {
        if (!$rootScope.isUserLoggedIn()) return false;
        for (let i = 0; i < $localStorage.webMarketUser.roles.length; i++) {
            if ($localStorage.webMarketUser.roles[i].includes(role)) return true;
        }
        return false;
    }
});