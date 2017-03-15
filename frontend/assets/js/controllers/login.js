'use strict';

/* Controllers */

angular.module('app')
    .factory('apiLogin', function($http){
        return {
            getApi: function(usuario){
                return $http({
                    method:'POST',
                    url:'http://192.198.90.26:82/musicsocial/usuario/login',
                    headers : {
                        'Content-Type': 'application/json'
                    },
                    data: {
                        login: 'usuario.login',
                        senha: 'usuario.senha'
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
                }
                else {
                    $scope.login.valid = false;   
                }
            })
                        
        }


    }]);
