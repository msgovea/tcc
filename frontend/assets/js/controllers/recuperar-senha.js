'use strict';

/* Controllers */

angular.module('app')
    .factory('apiRecuperarSenha', function($http){
        return{
            getApi: function(usuario){
                return $http({
                    method: 'POST',
                    url: 'assets/js/apps/email/testes.json',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data:{
                        email: usuario.email
                    }
                })
            }
        }

    })

    .controller('RecuperarSenhaCtrl', ['$scope', '$state','apiRecuperarSenha', function($scope, $state, apiRecuperarSenha) {
        $scope.recuperarSenha = function(user){
            var message = "lala!"
            //apiRecuperarSenha.getApi(user).then(function(result){
                if (message == "Sucesso!") {
                     $('body').pgNotification({
                        style: 'simple',
                        title: 'Right email',
                        message: 'Password recovery sent to the informed email',
                        position: 'top-right',
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();    
                }
                else{
                     $('body').pgNotification({
                        style: 'simple',
                        title: 'Wrong email',
                        message: 'Email not found ',
                        position: 'top-right',
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                }

           // })
        }
        
    }]);
