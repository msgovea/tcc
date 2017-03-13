'use strict';

/* Controllers */

angular.module('app')
    .controller('LoginCtrl', ['$scope', function($scope) {

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.validateLogin = function(user) { 
            //alert("Login Success :)");
            
        }


    }]);
