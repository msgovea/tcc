'use strict';

/* Controllers */

angular.module('app')
    // Social controller 
    .controller('FeedCtrl', ['$scope', '$stateParams', '$rootScope', '$http', '$filter','$cookieStore', '$base64', function($scope, $stateParams, $rootScope, $http, $filter, $cookieStore, $base64) {
        // Apply recommended theme for Calendar

        $scope.app.layout.theme = 'pages/css/themes/simple.css';

        // For demo purposes only. Changes the theme back to pages default when switching the state. 
        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.app.layout.theme = 'pages/css/pages.css';
            });

        $http.get('http://192.198.90.26:82/musicsocial/publicacoes/get/'+$base64.encode($cookieStore.get('usuario').codigoUsuario)).success(function(data){
            $scope.publicacoes = data.object;
            console.log(data.object);
        }); 

        $scope.filtrarNumero = function(numero) {
            var nu = String(numero);
            if (nu.length <= 4) {
                return $filter('number')(nu);
            } else {
                return $filter('limitTo')(nu,nu.length - 3) + 'K';
            }
        };

        $scope.post = function(publicacao){
            var pubTeste;
            if (publicacao != null && publicacao !=""){
                pubTeste=[{
                            "usuario": {"codigoUsuario": "33", 
                                        "cidade": "Mogi Mirim"},
                            "conteudo": "Testando a publicação!!!!",
                            "dataPublicacao": "2017-03-21"
                }];
                $scope.publicacoes.unshift(pubTeste);
                console.log($scope.publicacoes);
            }
            else{
                console.log("Não publicou");
            }
            
        }
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