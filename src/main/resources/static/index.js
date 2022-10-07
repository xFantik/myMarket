angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';

    $scope.loadProducts = function () {
        $http.get(contextPath + '/product/all')
            .then(function (response) {
                $scope.productList = response.data;
            });
    };


    $scope.deleteProduct = function (productId){
        $http({
            url: contextPath + '/product/deleteProduct', 
            method: 'GET',
            params: {
                productId: productId
                }
        
        
        }).then(function (response){
            $scope.loadProducts();
        });
    
    };
      $scope.addProduct = function (){
        $http({
            url: contextPath + '/product/addProduct',
            method: 'GET',
            params: {
                id: document.getElementById("input-id").value,
                title: document.getElementById("input-title").value,
                price: document.getElementById("input-price").value
                }

        }).then(function (response){
            document.getElementById("error_text").style.visibility='hidden';

            if (response.data == true){
                            document.getElementById("input-id").value = "";
                            document.getElementById("input-title").value = "";
                            document.getElementById("input-price").value = "";
                            $scope.loadProducts();
                        } else {
                            document.getElementById("error_text").style.visibility='visible';
                        };
        });

    };

    $scope.loadProducts();

});