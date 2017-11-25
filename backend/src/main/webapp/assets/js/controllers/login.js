'use strict';

/* Controllers */

angular.module('app')
    .factory('apiLogin', function($http, md5){
        return {
            getApi: function(user){
                
                return $http({
                    method:'POST',
                    url:'http://192.198.90.26:80/usuario/login',
                    headers: {
                        'Content-Type': 'application/json'
                        //'postman-token': '534835a0-f817-9f22-b88a-e1ed9f40d1ea'//, text/plain //undefined//'application/json'
                    },
                    //headers: "Content-Type: application/json;charset=UTF-8",
                    data: {
                        email: user.username,
                        senha: md5.createHash(user.password)
                    }
                })
            }
        }
    })



    .controller('LoginCtrl', ['$scope', 'apiLogin', '$state', '$timeout', '$cookieStore','$filter', '$window', '$translate', function($scope, apiLogin, $state, $timeout, $cookieStore, $filter, $window, $translate) {

        $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };

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
                    switch (result.data.object.situacaoConta.codigoSituacaoConta) {
                        case 0: //aguardando confirmacao
                            $scope.login2.$invalid = true;
                            $scope.loading = false;
                            $('body').pgNotification({
                                style: 'simple',
                                title: $filter('translate')('LOGIN.FORM.ERROR3_TITLE'),
                                message: $filter('translate')('LOGIN.FORM.ERROR3'),
                                position: 'top-right',
                                showClose: false,
                                timeout: 6000,
                                type: 'danger',
                                thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                            }).show();
                            break;

                        case 1: //conta ativa
                            $cookieStore.put('usuario', result.data.object);
                            $state.go('app.denuncias');
                            break;

                        case 2: //conta inativa
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
                            break;

                        case 3: //conta banida    
                            $scope.login2.$invalid = true;
                            $scope.loading = false;
                            $('body').pgNotification({
                                style: 'simple',
                                title: $filter('translate')('LOGIN.FORM.ERROR4_TITLE'),
                                message: $filter('translate')('LOGIN.FORM.ERROR4'),
                                position: 'top-right',
                                showClose: false,
                                timeout: 6000,
                                type: 'danger',
                                thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                            }).show();
                            break;
                    }
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
