'use strict';

/* Controllers */

angular.module('app')
    .controller('RecuperarSenhaCtrl', ['$scope', '$state','$filter','$base64', '$http', function($scope, $state, $filter, $base64,$http) {
        $scope.recuperarSenha = function(user){
            $scope.loading = true;
            $http.get('http://192.198.90.26:82/musicsocial/usuario/recuperar/'+$base64.encode(user.email)).success(function(result){
                if (result.message == "Sucesso!") {
                     $('body').pgNotification({
                        style: 'bar',
                        title: $filter('translate')('RECOVER.FORM.SUCCESS1_TITLE'),
                        message: $filter('translate')('RECOVER.FORM.SUCCESS1'),
                        position: 'top-right',
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show(); 
                    $state.go('access.login');   
                }
                else{
                    $scope.recuperar.$invalid = true;
                    $scope.loading = false;
                     $('body').pgNotification({
                        style: 'bar',
                        title: $filter('translate')('RECOVER.FORM.ERROR2_TITLE'),
                        message: $filter('translate')('RECOVER.FORM.ERROR2'),
                        position: 'top-right',
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                }

           })
        }
        
    }]);
