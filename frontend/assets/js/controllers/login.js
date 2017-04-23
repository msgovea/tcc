'use strict';

/* Controllers */

angular.module('app')
    .factory('apiLogin', function($http){
        return {
            getApi: function(user){
                
                return $http({
                    method:'POST',
                    url:'http://192.198.90.26:82/musicsocial/usuario/login',
                    headers: {
                        'Content-Type': 'application/json'
                        //'postman-token': '534835a0-f817-9f22-b88a-e1ed9f40d1ea'//, text/plain //undefined//'application/json'
                    },
                    //headers: "Content-Type: application/json;charset=UTF-8",
                    data: {
                        email: user.username,
                        senha: user.password
                    }
                })
            }
        }
    })



    .controller('LoginCtrl', ['$scope', 'apiLogin', '$state', '$timeout', '$cookieStore','$filter', function($scope, apiLogin, $state, $timeout, $cookieStore, $filter) {

        $scope.bg = 'assets/img/headphone' + Math.floor((Math.random()*4)+1) + '.jpg';

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.validateLogin = function(user3) { 
            //alert("Login Success :)");
            apiLogin.getApi(user3).then(function(result){
                //console.log(result);
                $scope.loading = true;
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    //console.log("sucesso");
                    //console.log($cookieStore.usuario);
                    $cookieStore.put('usuario', result.data.object);
                    //console.log($cookieStore.usuario);
                    $state.go('app.dashboard');
                  //  $scope.loading = false;
                }
                else {   
                    $scope.login2.$invalid = true;
                    $scope.loading = false;
                    $('body').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('LOGIN.FORM.ERROR2_TITLE'),
                        message: $filter('translate')('LOGIN.FORM.ERROR2'),
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
