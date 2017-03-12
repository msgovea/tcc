'use strict';

/* Controllers */

angular.module('app')
    .controller('RegisterCtrl', ['$scope', function($scope) {

    	 $scope.finished = function() {
            alert("Wizard finished :)");
        }

    }]);
