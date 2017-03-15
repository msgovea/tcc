'use strict';

/* Controllers */

angular.module('app')
    .factory('apiLogin', function($http){
        return {
            getApi: function(usuario){
                
                return $http({
                    method:'POST',
                    url:'http://192.198.90.26:82/musicsocial/usuario/login',
                    headers: {
                        'Content-Type': 'application/json'
                        //'postman-token': '534835a0-f817-9f22-b88a-e1ed9f40d1ea'//, text/plain //undefined//'application/json'
                    },
                    //headers: "Content-Type: application/json;charset=UTF-8",
                    data: {
                        email: usuario.username.$viewValue,
                        senha: usuario.password.$viewValue
                    }
                })
            }
        }
    })

    .controller('LoginCtrl', ['$scope', 'apiLogin', function($scope, apiLogin) {

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.validateLogin = function(login) { 
            //alert("Login Success :)");
            apiLogin.getApi(login).then(function(result){
                console.log(result);
                console.log("oi");
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    console.log("sucesso");
                    $scope.login.invalid = false;   

                }
                else {
                    $scope.login.invalid = true;   
                }
            })
                        
        }


    }]);
