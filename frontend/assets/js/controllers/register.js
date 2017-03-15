'use strict';

/* Controllers */

angular.module('app')
    .controller('RegisterCtrl', ['$scope', function($scope) {

    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.createAccount = function(user) { 
            //alert("Account created :)")

            
        }


    }]);
