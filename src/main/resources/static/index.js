angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = document.URL + 'api/v1';

    $scope.currentPage = 1



    $input = document.getElementById('input_min-id');
    $input2 = document.getElementById('input_max-id');
    $input3 = document.getElementById('input_title-id');
    $input4 = document.getElementById('current_page-id');


    $input.onchange = function () {
        document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = 1
        $scope.loadProducts();
    };
    $input2.onchange = function () {
        document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = 1
        $scope.loadProducts();
    };
    $input3.onchange = function () {
        document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = 1
        $scope.loadProducts();
    };
    $input4.onchange = function () {
        document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = document.getElementById("current_page-id").value;
        $scope.loadProducts();
    };


    // $scope.loadProducts = function () {
    //     $http.get(contextPath + '/product/all?min='+ document.getElementById("input_min-id").value+'&max='+ document.getElementById("input_max-id").value)
    //         .then(function (response) {
    //             $scope.productList = response.data;
    //         });
    // };


    $scope.loadProducts = function () {
        console.log(document.URL);
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

    $scope.goToPage = function (page) {
        document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = page;
        $scope.loadProducts();
    };

    $scope.deleteProduct = function (productId) {
        document.getElementById("error_text").style.visibility = 'hidden';
        $http({
            url: contextPath + '/products',
            method: 'DELETE',
            params: {
                productId: productId
            }


        }).then(function () {
            $scope.loadProducts();
        });

    };
    $scope.addProduct = function () {
        console.log($scope.newProductJson);
        $http.post(contextPath + '/products', $scope.newProductJson)
            .then(function () {

                $scope.loadProducts();
                document.getElementById("error_text").style.visibility = 'hidden';
                document.getElementById("error_text").value = 'Продукт добавлен';
                document.getElementById("error_text").style.visibility = 'visible';
                document.getElementById("error_text").style.color = 'green';


            })
            .catch(function (response) {
                if (response.status === 400) {
                    document.getElementById("error_text").value = response.data.message;
                    document.getElementById("error_text").style.visibility = 'visible';
                    document.getElementById("error_text").style.color = 'red';
                }

            });

    };
    $scope.loadProducts();

});