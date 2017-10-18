'use strict';

/* Controllers */

angular.module('app')

    .controller('RedefinirSenhaCtrl', ['$scope', '$state', '$stateParams', '$base64','$http','md5','$filter', function($scope, $state, $stateParams, $base64, $http, md5, $filter) {

    	$scope.finished = function() {
             $scope.redefinir.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.redefinirSenha = function(user){
            var idBase =    $stateParams.id;
            var emailHash = $stateParams.codigo;
            var senhaHash = md5.createHash(user.password);
            
            $scope.loading = true;
            $http.get('http://192.198.90.26:80/usuario/redefinir/'+idBase+'/'+emailHash+'/'+senhaHash).success(function(result){
                if (result.message == "Sucesso!") {
                    $('body').pgNotification({
                        style: 'bar',
                        title: $filter('translate')('RESET.FORM.SUCCESS1_TITLE'),
                        message: $filter('translate')('RESET.FORM.SUCCESS1'),
                        position: 'top-right',
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show(); 
                    $state.go('access.login');   
                }              
            });
    
        }; 
        
    }]);
