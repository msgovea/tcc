'use strict';

/* Controllers */

angular.module('app').factory('apiRegister', function($http, md5) {
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
                        senha: md5.createHash(usuario.password),
                        nivelUsuario: {
                            codigoNivel: 1
                        },
                        situacaoConta: {
                            codigoSituacaoConta: 0
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
    .controller('RegisterCtrl', ['$scope', 'apiRegister', '$cookieStore', '$state', '$filter', function($scope, apiRegister, $cookieStore, $state, $filter) {

		
        var today=new Date();
        var dia = today.getDate();
        var mes = today.getMonth()+1;
        var ano = today.getFullYear();
        var today2 = dia + "/" + mes + "/" + ano
        $scope.today = today2;
        //console.log(today2);

		$scope.validateDate = function(date) {
            var parts = $scope.today.split('/');
            var date2;

            if (date != undefined ){
                date2 = date.split('/');

                if (date2[1].indexOf("0") != -1){
                    var a = date2[1].split("");
                    date2[1] = a[1];
                }    
            
                if (date2[2] < parts[2]) {
                    //console.log('valido');
                    $scope.birthdayError = false;
                }
                else if (date2[2] == parts[2]){
                    if (date2[1] < parts[1]){
                        //console.log('valido');
                        $scope.birthdayError = false;
                    }
                    else if (date2[1] == parts [1]){
                        if (date2[0] < parts[0]){
                            //console.log('valido');
                            $scope.birthdayError = false;
                        }
                        else{
                            $scope.birthdayError = true;
                            //console.log('invalido');
                        }
                    }
                    else{
                        $scope.birthdayError = true;
                        //console.log('invalido');
                    }
                }
                else {
                    $scope.birthdayError = true;
                    //console.log('invalido');
                }
            }
		}
        

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.createAccount = function(user) { 
            console.log("Antes" + user.birthday);
            var parts = (user.birthday.split('/'));
            user.birthday = parts[2] + '-' + parts[1] + '-' + parts[0];
            console.log("Depois" + user.birthday);

            apiRegister.getApi(user).then(function(result){
                //console.log(result);
                //console.log("oi");
                
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    console.log("sucesso");
                    $cookieStore.put('usuario', result.data.object);
                    //console.log($cookieStore.usuario);
                    $('body').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('REGISTER.FORM.NOTIF1_TITLE'),
                        message: $filter('translate')('REGISTER.FORM.NOTIF1'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();

                    $state.go('access.login');
                }
                else {
                    console.log("fracasso");
                    $scope.register.invalid = true;  
                    $('body').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('REGISTER.FORM.ERROR10_TITLE'),
                        message: $filter('translate')('REGISTER.FORM.ERROR10'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show(); 
                }
            })
        }


    }]);
