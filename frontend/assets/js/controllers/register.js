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

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.validateDate = function () {

            var RegExPattern = /^((((0?[1-9]|[12]\d|3[01])[\.\-\/](0?[13578]|1[02])      [\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|[12]\d|30)[\.\-\/](0?[13456789]|1[012])[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|((0?[1-9]|1\d|2[0-8])[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?\d{2}))|(29[\.\-\/]0?2[\.\-\/]((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))|(((0[1-9]|[12]\d|3[01])(0[13578]|1[02])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|[12]\d|30)(0[13456789]|1[012])((1[6-9]|[2-9]\d)?\d{2}))|((0[1-9]|1\d|2[0-8])02((1[6-9]|[2-9]\d)?\d{2}))|(2902((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00))))$/;

            if (!(($scope.birthday.match(RegExPattern)) && ($scope.birthday!=''))) {
                alert('Data inválida.');
                id.focus();
            }
            else
                alert('Data válidaa.');
        }

        $scope.createAccount = function(user) { 
            apiRegister.getApi(user).then(function(result){
                console.log(result);
                console.log("oi");
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    //console.log("sucesso");
                    $cookieStore.put('usuario', result.data.object);
                    //console.log($cookieStore.usuario);

                    $state.go('app.dashboard');
                }
                else {
                    $scope.register.invalid = true;   
                }
            })
        }


    }]);
