'use strict';

/* Controllers */

angular.module('app').factory('apiSalvarEdic', function($http) {
        
        return {
            getApi: function(usuario) {

                return $http({
                    method: 'POST',
                    url: 'http://192.198.90.26:82/usuario/atualizar',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: usuario
                })
            }
        }
    })

    .controller('HeaderCtrl', ['$scope', '$cookieStore', '$state' , '$translate', 'md5', 'apiSalvarEdic','$filter', function($scope, $cookieStore, $state, $translate, md5, apiSalvarEdic, $filter) {

        $scope.user = $cookieStore.get('usuario');

        $scope.logout = function() {
            $cookieStore.remove('usuario');
            $state.go('access.login');
        }

         $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };

         $scope.finished = function() {
             $scope.altSenha.passequal = ($scope.usSenha.password == $scope.usSenha.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.AlterarSenha = function(usSenha){
            $scope.user.senha = md5.createHash(usSenha.password);

            $scope.loading = true;
            apiSalvarEdic.getApi($scope.user).then(function(result){
                
                if (result.data.message == "Sucesso!") {
                    $cookieStore.put('usuario', result.data.object);
                    $scope.user = $cookieStore.get('usuario');

                    $('#modalAltSenha').modal('hide'); 
                    $('body').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('Sucesso'),
                        message: $filter('translate')('Alteração realizada com sucesso!'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                }
                else {
                    $scope.social.$invalid = true; 
                    $scope.loading = false; 
                     $('#modalEdDadosPe').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('Falha'),
                        message: $filter('translate')('Não foi possível realizar as alterações.'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show(); 
                }
            })

        }

        $scope.validSenha = function(){
            if ($scope.usSenha.passAtu != null){
                $scope.altSenha.passCorr = ($scope.user.senha == md5.createHash($scope.usSenha.passAtu)) ? false : true;
            }
        }
    }]);
