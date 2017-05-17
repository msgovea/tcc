'use strict';

/* Controllers */

angular.module('app')
    .factory('apiEfetuarPub', function($http){
        return {
            getApi: function(user, post) {
                return $http({
                    method: 'POST',
                    url: 'http://192.198.90.26:82/musicsocial/publicacoes/cadastrar',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: {
                        conteudo: post,
                        usuario: {
                            user
                        }
                    }
                })
            }
        }
    })
    // Social controller 
    .controller('FeedCtrl', ['$scope', '$stateParams', '$rootScope', '$http', '$filter', '$cookieStore','$base64', '$state',  function($scope, $stateParams, $rootScope, $http, $filter, $cookieStore, $base64, $state) {
        // Apply recommended theme for Calendar

        $scope.app.layout.theme = 'pages/css/themes/simple.css';
        
        console.log($cookieStore.get('usuario').codigoUsuario);

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
            

            if (publicacao != null && publicacao !=""){
               var publi = {};
                publi.usuario = $cookieStore.get('usuario');
                publi.conteudo = publicacao;

                $http.post(
                    'http://192.198.90.26:82/musicsocial/publicacoes/cadastrar', 
                    publi            
                ).success(function (response) {
                    if(response.message === 'Sucesso!'){
                        /*$http.get('http://192.198.90.26:82/musicsocial/publicacoes/get/'+$base64.encode($cookieStore.get('usuario').codigoUsuario)).success(function(data){
                            $scope.publicacoes = data.object;
                            console.log(data.object);
                        });*/
                        $state.reload();
                    }
                });
            } else{
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