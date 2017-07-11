'use strict';

/* Controllers */

angular.module('app')
    // Social controller 
    .controller('SocialCtrl', ['$scope', '$stateParams', '$rootScope', function($scope, $stateParams, $rootScope) {
        // Apply recommended theme for Calendar
        $scope.app.layout.theme = 'pages/css/themes/simple.css';

        // For demo purposes only. Changes the theme back to pages default when switching the state. 
        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams) {
                $scope.app.layout.theme = 'pages/css/themes/simple.css';
            })
        
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