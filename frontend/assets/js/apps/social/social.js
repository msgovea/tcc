'use strict';

/* Controllers */

angular.module('app').factory('apiSalvarEdic', function($http) {
        
        return {
            getApi: function(usuario, dNasc) {
                console.log("Linha 6");
                console.log(usuario);
                var parts = (dNasc.split('/'));
                var dataNasc = parts[2] + '-' + parts[1] + '-' + parts[0];
                usuario.dataNascimento = dataNasc;
                return $http({
                    method: 'POST',
                    url: 'http://192.198.90.26:82/musicsocial/usuario/atualizar',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: usuario
                })
            }
        }
    })

    .controller('SocialCtrl', ['$scope', '$stateParams', '$rootScope', 'apiSalvarEdic','$filter', '$cookieStore', '$http', function($scope, $stateParams, $rootScope, apiSalvarEdic, $filter, $cookieStore, $http) {
        // Apply recommended theme for Calendar
        $scope.app.layout.theme = 'pages/css/themes/simple.css';
        var parts = $scope.user.dataNascimento.split('-');
        $scope.diaNasc = parts[2];
        $scope.mesNasc = parts[1];
        $scope.anoNasc = parts[0];
        $scope.gostos = $scope.user.gostosMusicais;
        $scope.gostosAPI = [];
        $scope.gostoFavAPI = {favorito: null};

        $http.get('http://192.198.90.26:82/musicsocial/usuario/getGostosMusicais').success(function(result) {
            for(var i = 0; i < result.object.length; i++){
                $scope.gostosAPI[i] = result.object[i]; 
            }
        });

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
        
        var parts = $scope.user.dataNascimento.split('-'); 
        $scope.diaNasc = parts[2]; 
        $scope.mesNasc = parts[1]; 
        $scope.anoNasc = parts[0]; 
        $scope.dataNascimento = $scope.diaNasc + '/' + $scope.mesNasc + '/' + $scope.anoNasc
        $scope.gostos = $scope.user.gostosMusicais; 
        for(var i = 0; i < $scope.gostos.length; i++){ 
            if ($scope.gostos[i].favorito == true){ 
                $scope.gostoFavorito = $scope.gostos[i];  
            } 
        } 


        var today=new Date();
        var dia = today.getDate();
        var mes = today.getMonth()+1;
        var ano = today.getFullYear();
        var today2 = dia + "/" + mes + "/" + ano
        $scope.today = today2;
        //console.log(today2);

		$scope.validateDate = function(date) {
            var parts = $scope.today.split('/');
            var date2;

            if (date != undefined ){
                date2 = date.split('/');

                if (date2[1].indexOf("0") != -1){
                    var a = date2[1].split("");
                    date2[1] = a[1];
                }    
            
                if (date2[2] < parts[2]) {
                    //console.log('valido');
                    $scope.birthdayError = false;
                }
                else if (date2[2] == parts[2]){
                    if (date2[1] < parts[1]){
                        //console.log('valido');
                        $scope.birthdayError = false;
                    }
                    else if (date2[1] == parts [1]){
                        if (date2[0] < parts[0]){
                            //console.log('valido');
                            $scope.birthdayError = false;
                        }
                        else{
                            $scope.birthdayError = true;
                            //console.log('invalido');
                        }
                    }
                    else{
                        $scope.birthdayError = true;
                        //console.log('invalido');
                    }
                }
                else {
                    $scope.birthdayError = true;
                    //console.log('invalido');
                }
            }
		};

        $scope.salvarEdic = function(user, dtNasc){
            $scope.loading = true;
            console.log("Linha 115");
            console.log(user)
            apiSalvarEdic.getApi(user, dtNasc).then(function(result){
                //console.log(result);
                //console.log("oi");
                
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    console.log("sucesso");
                    $cookieStore.put('usuario', result.data.object);
                    //console.log($cookieStore.usuario);
                    $('#modalEdDadosPe').modal('hide'); 
                    $('body').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('Sucesso'),
                        message: $filter('translate')('Alteração realizada com sucesso!'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'success',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                }
                else {
                    console.log("fracasso");
                    $scope.social.$invalid = true; 
                    $scope.loading = false; 
                     $('#modalEdDadosPe').pgNotification({
                        style: 'simple',
                        title: $filter('translate')('Falha'),
                        message: $filter('translate')('Não foi possível realizar as alterações.'),
                        position: 'top-right',
                        showClose: false,
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show(); 
                }
            })
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