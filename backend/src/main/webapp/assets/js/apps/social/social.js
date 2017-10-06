'use strict';

/* Controllers */

angular.module('app').factory('apiSalvarEdic', function($http) {
        
        return {
            getApi: function(usuario) {

                return $http({
                    method: 'POST',
                    url: 'http://192.198.90.26:80/usuario/atualizar',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: usuario
                })
            }
        }
    })

    .controller('SocialCtrl', ['$scope', '$stateParams', '$rootScope', 'apiSalvarEdic','$filter', '$cookieStore', '$http',  function($scope, $stateParams, $rootScope, apiSalvarEdic, $filter, $cookieStore, $http) {
        // Apply recommended theme for Calendar
        $scope.app.layout.theme = 'pages/css/themes/simple.css';
        
        var parts = $scope.user.dataNascimento.split('-');
        

        $scope.diaNasc = parts[2];
        $scope.mesNasc = parts[1];
        $scope.anoNasc = parts[0];
        $scope.dataNascimento = parts[2] + '/' + parts[1] + '/' + parts[0]
        $scope.gostos = $scope.user.gostosMusicais;
        $scope.gostosAPI = [];
        $scope.gostoFavAPI = {favorito: null};
        $rootScope.gostosCadastrados = [];

        for(var i = 0; i < $scope.gostos.length; i++){
            if ($scope.gostos[i].favorito == true){
                $scope.gostoFavorito = $scope.gostos[i]; 
            }
        }
        
        function copiarObj(obj) {
            if (obj === null || typeof obj !== 'object') {
                return obj;
            }
            var temp = obj.constructor();
            for (var key in obj) {
                temp[key] = copiarObj(obj[key]);
            }
            return temp;
        }

        $http.get('http://192.198.90.26:80/usuario/buscar/'+ $stateParams.codUser).success(function(result){
            $scope.lPerUsu = true;
            $scope.userPage = result.object;


            if ($scope.userPage.codigoUsuario != $scope.user.codigoUsuario)
                $scope.lPerUsu = false;
            
            $scope.usuCadastrado = copiarObj($scope.userPage);
            
            console.log($scope.usuCadastrado);
        })
        $http.get('http://192.198.90.26:80/usuario/getGostosMusicais').success(function(result) {
            for(var i = 0; i < result.object.length; i++){
                $scope.gostosAPI[i] = result.object[i]; 
                for (var j = 0; j < $scope.gostos.length; j++){
                    if ($scope.gostosAPI[i].codigo == $scope.gostos[j].pk.gostoMusical.codigo){
                        $scope.gostosAPI[i].selecionado = true;
                    }
                }
            }
        })

       

        
        // For demo purposes only. Changes the theme back to pages default when switching the state. 
        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.app.layout.theme = 'pages/css/themes/simple.css';
            })


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
            var parts = (dtNasc.split('/'));
            var dataNasc = parts[2] + '-' + parts[1] + '-' + parts[0];
            
            $scope.loading = true;
            user.dataNascimento = dataNasc;
            apiSalvarEdic.getApi(user).then(function(result){
                //console.log(result);
                //console.log("oi");
                
                if (result.data.message == "Sucesso!") {
                    $cookieStore.put('usuario', result.data.object);
                    $scope.user = $cookieStore.get('usuario');
                    
                    location.reload();


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

        $scope.fecharModDPe = function (){
            $scope.usuCadastrado = copiarObj($scope.user);
            $('#modalEdDadosPe').modal('hide');
        }

        $scope.cadastrarGostos = function (){
            var i = 0;
            for(var j = 0; j < $scope.gostosAPI.length; j++){
                if ($scope.gostosAPI[j].selecionado){
                    i += 1
                    $rootScope.gostosCadastrados.push($scope.gostosAPI[j]);
                }
            }

            for(var j = 0; j < $rootScope.gostosCadastrados.length; j++){
                if ($rootScope.gostosCadastrados[j].selecionado){
                    $rootScope.gostosCadastrados[j].selecionado = "";
                }
            }

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
                $('#modalGostos').modal('hide'); 
                $('#modalGostoFavorito').modal('show');
            }   
        }

        
       
        $scope.cadastrarGostoFavorito = function(){
            var objeto  = {};
            var usuario = {};
            var tamanho = $scope.user.gostosMusicais.length;

            for(var i = 0; i < tamanho; i++){
                $scope.user.gostosMusicais.shift();
            }

            /*objeto.codigoUsuario = $cookieStore.get('usuario').codigoUsuario; 
            objeto.codigosGostosMusicais= [];*/

            for(var i = 0; i < $rootScope.gostosCadastrados.length; i++){
                $scope.user.gostosMusicais.push($scope.gostosCadastrados[i].codigo);
            }
            objeto.favorito =  $scope.gostoFavorito.favorito;


            /*console.log($scope.gostosCadastrados);
            console.log($scope.user.gostosMusicais);
            $scope.user.gostosMusicais = $scope.gostosCadastrados

            console.log($scope.user.gostosMusicais);*/

            $http.post(
                'http://192.198.90.26:80/usuario/gostosmusicais', 
                objeto
            ).success(function(response){
                if(response.message === 'Sucesso!'){
                    $http.post(
                        'http://192.198.90.26:80/usuario/login', 
                        usuario
                    ).success(function(retorno){
                        if(retorno.message === 'Sucesso!'){
                            console.log(retorno);
                            $cookieStore.put('usuario', retorno.object);
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
                    })
                   
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