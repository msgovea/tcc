'use strict';

/* Controllers */

angular.module('app')
    .factory('apiLogin', function($http){
        return {
            getApi: function(user){
                
                return $http({
                    method:'POST',
                    url:'http://192.198.90.26:82/musicsocial/usuario/login',
                    headers: {
                        'Content-Type': 'application/json'
                        //'postman-token': '534835a0-f817-9f22-b88a-e1ed9f40d1ea'//, text/plain //undefined//'application/json'
                    },
                    //headers: "Content-Type: application/json;charset=UTF-8",
                    data: {
                        email: user.username,
                        senha: user.password
                    }
                })
            }
        }
    })



    .controller('LoginCtrl', ['$scope', 'apiLogin', '$state', '$timeout', 'Facebook', '$cookies', function($scope, apiLogin, $state, $timeout, Facebook, $cookies) {

        $cookies.usuario = {
            id: 'bla',
            senha: 'bla',
            nivel_acesso: 'bla'
        };

        console.log($cookies.usuario);

        /* 
        CONFIGURAÇÃO FACEBOOK -- INICIO
         */

         // Define user empty data :/
      $scope.user2 = {};
      
      // Defining user logged status
      $scope.logged = false;
      
      // And some fancy flags to display messages upon user status change
      $scope.byebye = false;
      $scope.salutation = false;
      
      /**
       * Watch for Facebook to be ready.
       * There's also the event that could be used
       */
      $scope.$watch(
        function() {
          return Facebook.isReady();
        },
        function(newVal) {
          if (newVal)
            $scope.facebookReady = true;
        }
      );
      
      var userIsConnected = false;
      
      Facebook.getLoginStatus(function(response) {
        if (response.status == 'connected') {
          userIsConnected = true;
        }
      });
      
      /**
       * IntentLogin
       */
      $scope.IntentLogin = function() {
        if(!userIsConnected) {
          $scope.login2();
        }
      };
      
      /**
       * Login
       */
       $scope.login2 = function() {
         Facebook.login(function(response) {
          if (response.status == 'connected') {
            $scope.logged = true;
            $scope.me();
          }
        
        });
       };
       
       /**
        * me 
        */
        $scope.me = function() {
          Facebook.api('/me', function(response) {
            /**
             * Using $scope.$apply since this happens outside angular framework.
             */
            $scope.$apply(function() {
              $scope.user2 = response;
            });
            
          });
        };
      
      /**
       * Logout
       */
      $scope.logout = function() {
        Facebook.logout(function() {
          $scope.$apply(function() {
            $scope.user2   = {};
            $scope.logged = false;  
          });
        });
      }
      
      /**
       * Taking approach of Events :D
       */
      $scope.$on('Facebook:statusChange', function(ev, data) {
        console.log('Status: ', data);
        if (data.status == 'connected') {
          $scope.$apply(function() {
            $scope.salutation = true;
            $scope.byebye     = false;    
          });
        } else {
          $scope.$apply(function() {
            $scope.salutation = false;
            $scope.byebye     = true;
            
            // Dismiss byebye message after two seconds
            $timeout(function() {
              $scope.byebye = false;
            }, 2000)
          });
        }
        
        
      });


        /*
        CONFIGURAÇÃO FACEBOOK -- FIM */


    	$scope.finished = function() {
             $scope.register.passequal = ($scope.user.password == $scope.user.cpassword) ? false : true; 
            //alert("Wizard finished :)");
        }

        $scope.validateLogin = function(user) { 
            //alert("Login Success :)");
            apiLogin.getApi(user).then(function(result){
                console.log(result);
                console.log("oi");
                if (result.data.message == "Sucesso!") {
                    //redireciona
                    console.log("sucesso");
                    $scope.login.invalid = false;   
                    $state.go('app.dashboard');
                }
                else {
                    $('body').pgNotification({
                        style: 'simple',
                        title: 'Login incorreto',
                        message: 'Usuário ou senha inválidos',
                        position: 'top-right',
                        timeout: 6000,
                        type: 'danger',
                        thumbnail: '<img width="40" height="40" style="display: inline-block;" src="" ui-jq="unveil"  alt="">'
                    }).show();
                    $scope.login.invalid = true;   
                }
            })
                        
        }


    }]);
