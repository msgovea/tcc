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
                        
                        email: usuario.email,
                        senha: usuario.password,
                        nivelUsuario: {
                            codigoNivel: 1
                        },
                        tipoConexao: {
                            codigoTipoConexao: 1
                        },
                        nome: usuario.name,
                        apelido: usuario.username,
                        dataNascimento: usuario.birthday,
                        cidade: usuario.city,
                        estado: usuario.state,
                        pais: usuario.country
                    }
                })
            }
        }
    })
    .controller('RegisterCtrl', ['$scope', 'apiRegister', '$cookieStore', '$state', function($scope, apiRegister, $cookieStore, $state) {

        var today=new Date();
        $scope.today = today.toISOString();
        console.log(today);
        

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.createAccount = function(user) { 
            console.log($scope.register.name);
            apiRegister.getApi(user).then(function(result){
                //console.log(result);
                //console.log("oi");
                
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    console.log("sucesso");
                    $cookieStore.put('usuario', result.data.object);
                    //console.log($cookieStore.usuario);

                    $state.go('app.dashboard');
                }
                else {
                    console.log("fracasso");
                    $scope.register.invalid = true;   
                }
            })
        }


    }]);
