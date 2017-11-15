'use strict';

/* Controllers */

angular.module('app')
    .controller('ConfirmaCadastroCtrl', ['$scope', '$state','$http', '$window','$stateParams','$filter', /*'apiGostoMusical',*/ function($scope, $state, $http, $window, $stateParams, $filter /*, apiGostoMusical*/) {

        $scope.confirmar = function() {
            $http.get('http://192.198.90.26:80/usuario/confirmar/' + $stateParams.idUsuario + '/' + $stateParams.emailHash).success(function(data) {
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
                    $window.location.href = "https://r789f.app.goo.gl/?link=http://www.urmusic.me/?confirmar&apn=com.mgovea.urmusic";
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