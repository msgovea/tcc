'use strict';

/* Controllers */

angular.module('app')

    .controller('RedefinirSenhaCtrl', ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams) {

    	$scope.finished = function() {
             $scope.redefinir.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.redefinirSenha = function(user){
            var id = $stateParams.id;
            var code = $stateParams.codigo;

            alert(id);      
            alert(code);
        }
    
        
        
    }]);
