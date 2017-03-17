'use strict';

/* Controllers */

angular.module('app').factory('apiRegister', function($http) {
        return {
            getApi: function(usuario) {
                return $http({
                    method: 'POST',
                    url: 'http://192.198.90.26:82/musicsocial/usuario/cadastro',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: {
                        
                        email: usuario.email.$viewValue,
                        senha: usuario.password.$viewValue,
                        nivelUsuario: {
                            codigoNivel: 1
                        },
                        tipoConexao: {
                            codigoTipoConexao: 1
                        },
                        nome: usuario.name.$viewValue,
                        apelido: usuario.username.$viewValue,
                        dataNascimento: '1995-10-10',
                        cidade: 'Campinas',
                        estado: 'SP',
                        pais: 'Brasil'

                    }
                })
            }
        }
    })
    .controller('RegisterCtrl', ['$scope', 'apiRegister', function($scope, apiRegister) {

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.createAccount = function(user) { 
            apiRegister.getApi(user).then(function(result){
                console.log(result);
                console.log("oi");
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    console.log("sucesso");
                    $scope.register.invalid = false;   

                }
                else {
                    $scope.register.invalid = true;   
                }
            })
        }


    }]);
