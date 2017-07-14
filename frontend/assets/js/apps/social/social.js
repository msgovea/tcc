'use strict';

/* Controllers */

angular.module('app')
    // Social controller 
    .controller('SocialCtrl', ['$scope', '$stateParams', '$rootScope', function($scope, $stateParams, $rootScope) {
        // Apply recommended theme for Calendar
        $scope.app.layout.theme = 'pages/css/themes/simple.css';
        var parts = $scope.user.dataNascimento.split('-');
        $scope.diaNasc = parts[2];
        $scope.mesNasc = parts[1];
        $scope.anoNasc = parts[0];
        $scope.gostos = $scope.user.gostosMusicais;
        for(var i = 0; i < $scope.gostos.length; i++){
            if ($scope.gostos[i].favorito == true){
                $scope.gostoFavorito = $scope.gostos[i]; 
            }
        }
        // For demo purposes only. Changes the theme back to pages default when switching the state. 
        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.app.layout.theme = 'pages/css/themes/simple.css';
            })

    }]);

/* Directives */

angular.module('app')
    .directive('pgSocial', function() {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var $social = $(element);
                $social.social($social.data());
            }
        }
    });