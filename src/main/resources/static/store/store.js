
angular.module('market-front').controller('storeController', function ($rootScope, $scope, $http, $location, $localStorage) {
    const contextPath = 'http://' + window.location.hostname + ':' + window.location.port + '/market/api/v1';
    $scope.currentPage = 1

    $input = document.getElementById('input_min-id');
    $input2 = document.getElementById('input_max-id');
    $input3 = document.getElementById('input_title-id');
    $input4 = document.getElementById('current_page-id');


    $input.onchange = function () {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = 1
        $scope.loadProducts();
    };
    $input2.onchange = function () {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = 1
        $scope.loadProducts();
    };
    $input3.onchange = function () {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = 1
        $scope.loadProducts();
    };
    $input4.onchange = function () {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = document.getElementById("current_page-id").value;
        $scope.loadProducts();
    };


    $scope.loadProducts = function () {
        if ($scope.currentPage < 1) {
            $scope.currentPage = 1;
        }
        if ($scope.currentPage > $scope.pagesCount) {
            $scope.currentPage = $scope.pagesCount;
        }
        $http({
                url: contextPath + '/products',
                method: 'GET',
                params: {
                    minPrice: document.getElementById("input_min-id").value,
                    maxPrice: document.getElementById("input_max-id").value,
                    partName: document.getElementById("input_title-id").value,
                    active: !($rootScope.hasUserRole('ADMIN') || $rootScope.hasUserRole('MANAGER')),
                    page: $scope.currentPage
                }
            }
        ).then(function (response) {
            $scope.productList = response.data.content;
            $scope.pagesCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;
            document.getElementById("current_page-id").value = $scope.currentPage;
        });
    };
    //
    // $scope.generatePagesIndexes = function (startPage, endPage) {
    //     let arr = [];
    //     for (let i = startPage; i < endPage + 1; i++) {
    //         arr.push(i);
    //     }
    //     return arr;
    // }

    $scope.navToEditProductPage = function (productId) {
        $location.path('/edit_product/' + productId);
    }

    $scope.goToPage = function (page) {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = page;
        $scope.loadProducts();
    };

    $scope.addToCart = function (productId) {
        // document.getElementById("error_text").style.visibility = 'hidden';

        if ($scope.isUserLoggedIn()){
            console.log(productId);

            $http({
                url: contextPath + '/cart',
                method: 'POST',
                params: {
                    productId: productId
                }


            }).then(function () {
                $scope.loadProducts();
            }).catch(function (err){
                console.log ('вернулась ошибка')
                alert("Пожалуйста, авторизуйтесь снова");
                $scope.clearUser();
            })}
        else {
            alert("Пожалуйста, авторизуйтесь!");
        };

    };

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };


    $scope.loadProducts();
});