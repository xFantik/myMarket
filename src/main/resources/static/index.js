angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';

    $scope.currentPage = 0


    $input = document.getElementById('input_min-id');
    $input2 = document.getElementById('input_max-id');
    $input3 = document.getElementById('input_title-id');
    $input4 = document.getElementById('current_page-id');


    $input.onchange = function () {
        $scope.currentPage = 0
        $scope.loadProducts();
    };
    $input2.onchange = function () {
        $scope.currentPage = 0
        $scope.loadProducts();
    };
    $input3.onchange = function () {
        $scope.currentPage = 0
        $scope.loadProducts();
    };
    $input4.onchange = function () {
        $scope.currentPage = document.getElementById("current_page-id").value - 1;
        $scope.loadProducts();
    };


    // $scope.loadProducts = function () {
    //     $http.get(contextPath + '/product/all?min='+ document.getElementById("input_min-id").value+'&max='+ document.getElementById("input_max-id").value)
    //         .then(function (response) {
    //             $scope.productList = response.data;
    //         });
    // };


    $scope.loadProducts = function () {
        if ($scope.currentPage < 0) {
            $scope.currentPage = 0;
        }
        ;
        if ($scope.currentPage+1 > $scope.pagesCount) {
            $scope.currentPage = $scope.pagesCount-1;
        }
        ;
        $http({
                url: contextPath + '/product/find',
                method: 'GET',
                params: {
                    minPrice: document.getElementById("input_min-id").value,
                    maxPrice: document.getElementById("input_max-id").value,
                    partName: document.getElementById("input_title-id").value,
                    page: $scope.currentPage + 1
                }
            }
        ).then(function (response) {
            $scope.productList = response.data.content;
            $scope.pagesCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber;
            document.getElementById("current_page-id").value = $scope.currentPage + 1;
        });
    };

    $scope.goToPage = function (page) {
        $scope.currentPage = page;
        $scope.loadProducts();
    };

    $scope.deleteProduct = function (productId) {
        $http({
            url: contextPath + '/product/deleteProduct',
            method: 'GET',
            params: {
                productId: productId
            }


        }).then(function (response) {
            $scope.loadProducts();
        });

    };
    $scope.addProduct = function () {
        $http({
            url: contextPath + '/product/addProduct',
            method: 'GET',
            params: {
                title: document.getElementById("input-title").value,
                price: document.getElementById("input-price").value
            }

        }).then(function (response) {
            document.getElementById("error_text").style.visibility = 'hidden';

            if (response.data == true) {

                document.getElementById("input-title").value = "";
                document.getElementById("input-price").value = "";
                $scope.loadProducts();
            } else {
                document.getElementById("error_text").style.visibility = 'visible';
            }
            ;
        });

    };
    $scope.loadProducts();

});