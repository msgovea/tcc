'use strict';

/* Controllers */

angular.module('app')
    .controller('HeaderCtrl', ['$scope', '$cookieStore', '$state' , '$translate', function($scope, $cookieStore, $state, $translate) {

        $scope.user = $cookieStore.get('usuario');

        $scope.logout = function() {
            $cookieStore.remove('usuario');
            $state.go('access.login');
        }

         $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };
    }]);
