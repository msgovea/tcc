/* ============================================================
 * File: run.js
 * Configure run application
 * ============================================================ */

angular.module('app')
    .run(['$rootScope', '$state','$cookieStore', function($rootScope, $state, $cookieStore){
        $rootScope.$on('$stateChangeStart', function(event, toState, toParam, fromState, fromParam) {

            //console.log(event);
            //console.log(toState);
            //console.log(toParam);
            //console.log(fromState);
            //console.log(fromParam);

        if(toState.data.requireLogin) {
            function validarAutenticacao() {
                if ($cookieStore.get('usuario').id == undefined){
                   return false;
                }
                else{
                    return true;
                }                    
                //TODO 
                /*
                FAZER A VALIDAÇÃO COM OS DADOS QUE ESTÃO SALVOS NO COOKIE OU SEI LA!
                */
            };
            
            
            if (validarAutenticacao() == false) { //Se a autenticação for falsa
                event.preventDefault(); //Impede que a página inicie o carregamento
                $state.go('access.login'); 
                //Redirecione o usuario para a página de login.
            }
        }
    })
    }]);