'use strict';

/* Controllers */

angular.module('app')
    .controller('ConfirmaCadastroCtrl', ['$scope', '$state','$http', '$window','$stateParams','$filter', /*'apiGostoMusical',*/ function($scope, $state, $http, $window, $stateParams, $filter /*, apiGostoMusical*/) {
        //$scope.publicacoes = apiGostoMusical.getApi()
        $scope.gostos = []
        $http.get('assets/js/api/mock_gosto_musical.json').success(function(data) {
            for(var i = 0; i < data.object.length; i++){
                $scope.gostos[i] = data.object[i]; 
            }
            console.log($scope.gostos);
        });

        $scope.cadastrarGostos = function (){
            for(var j = 0; j < $scope.gostos.length; j++){
                if ($scope.gostos[j].selecionado){
                    console.log($scope.gostos[j]);
                //mandar para API para gravar
                }
            }
        }

        $scope.confirmar = function() {
            $http.get('http://192.198.90.26:82/musicsocial/usuario/confirmar/' + $stateParams.idUsuario + '/' + $stateParams.emailHash).success(function(data) {
                $scope.loading = true;
                if(data.message === 'Sucesso!') {
                    $('body').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('CONFIRM_REG.SUCCESS1_TITLE'),
                        message: $filter('translate')('CONFIRM_REG.SUCCESS1'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                     $state.go('access.login');
                }
                else{
                    $scope.confirm.$invalid = true;
                    $scope.loading = false;
                     $('body').pgNotification({
                        style: 'bar',
                        title: $filter('translate')('CONFIRM_REG.ERROR1_TITLE'),
                        message: $filter('translate')('CONFIRM_REG.ERROR1'),
                        position: 'top-right',
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                }
            });
        }
    }]);