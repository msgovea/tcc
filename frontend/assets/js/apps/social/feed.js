'use strict';

/* Controllers */

angular.module('app')
    // Social controller 
    .controller('FeedCtrl', ['$scope', '$stateParams', '$rootScope', '$http', '$filter', function($scope, $stateParams, $rootScope, $http, $filter) {
        // Apply recommended theme for Calendar
        $scope.app.layout.theme = 'pages/css/themes/simple.css';

        // For demo purposes only. Changes the theme back to pages default when switching the state. 
        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.app.layout.theme = 'pages/css/pages.css';
            });
        $http.get('assets/js/api/mock_social.json').success(function(data) {
            $scope.publicacoes = data.object;
        });
        $scope.filtrarNumero = function(numero) {
            var nu = String(numero);
            if (nu.length <= 4) {
                return $filter('number')(nu);
            } else {
                return $filter('limitTo')(nu,nu.length - 3) + 'K';
            }
        };
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