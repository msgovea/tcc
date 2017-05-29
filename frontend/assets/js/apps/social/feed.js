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

        // For demo purposes only. Changes the theme back to pages default when switching the state. 
        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.app.layout.theme = 'pages/css/pages.css';
            });

        $http.get('http://192.198.90.26:82/musicsocial/publicacoes/get/'+$base64.encode($cookieStore.get('usuario').codigoUsuario)).success(function(data){
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
                        location.reload();
                    }
                });
            } else{
                console.log("Não publicou");
            }
            
        }
        
        $('#modalSlideUp').modal('show');

        $scope.gostos = [];
        $rootScope.gostosCadastrados = [];
        $scope.gostoFavorito = {favorito: null};

        $http.get('http://192.198.90.26:82/musicsocial/usuario/getGostosMusicais').success(function(result) {
            for(var i = 0; i < result.object.length; i++){
                $scope.gostos[i] = result.object[i]; 
            }
        });

        $scope.cadastrarGostoFavorito = function(){
            var objeto = {};
            objeto.codigoUsuario = $cookieStore.get('usuario').codigoUsuario; 
            objeto.codigosGostosMusicais= [];
            for(var i = 0; i < $rootScope.gostosCadastrados.length; i++){
                objeto.codigosGostosMusicais.push($scope.gostosCadastrados[i].codigo);
            }
            objeto.favorito =  $scope.gostoFavorito.favorito;

            $http.post(
                'http://192.198.90.26:82/musicsocial/usuario/gostosmusicais', 
                objeto
            ).success(function(response){
                if(response.message === 'Sucesso!'){
                    
                    $('#modalGostoFavorito').modal('hide'); 
                    $('body').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('GOSTO_MUSICAL.SUCCESS_TITLE'),
                        message: $filter('translate')('GOSTO_MUSICAL.SUCCESS'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                }
                else {
                    $('#modalGostoFavorito').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('GOSTO_MUSICAL.ERROR1_TITLE'),
                        message: $filter('translate')('GOSTO_MUSICAL.ERROR1'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show(); 
                }
            })
        }

        $scope.cadastrarGostos = function (){
            var i = 0;
            for(var j = 0; j < $scope.gostos.length; j++){
                if ($scope.gostos[j].selecionado){
                    i += 1
                    $rootScope.gostosCadastrados.push($scope.gostos[j]);
                }
            }

            for(var j = 0; j < $rootScope.gostosCadastrados.length; j++){
                if ($rootScope.gostosCadastrados[j].selecionado){
                    $rootScope.gostosCadastrados[j].selecionado = "";
                }
            }

            console.log($rootScope.gostosCadastrados);

            if (i == 0){
                $('#modalSlideUp').pgNotification({
                    style: 'simple',
                    title: $filter('translate')('GOSTO_MUSICAL.ERROR2_TITLE'),
                    message: $filter('translate')('GOSTO_MUSICAL.ERROR2'),
                    position: 'top-right',
                    showClose: false,
                    timeout: 6000,
                    type: 'danger',
                    thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                }).show(); 
            }   
            else{
                $('#modalSlideUp').modal('hide'); 
                $('#modalGostoFavorito').modal('show');
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