'use strict';

/* Controllers */

angular.module('app')
    .controller('HeaderCtrl', ['$scope', '$cookieStore', '$state' , function($scope, $cookieStore, $state) {

        $scope.user = $cookieStore.get('usuario');
        console.log($cookieStore.get('usuario'));

        $scope.logout = function() {
            $cookieStore.remove('usuario');
            $state.go('access.login');
        }
    }]);
