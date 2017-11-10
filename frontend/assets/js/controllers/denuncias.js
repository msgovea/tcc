'use strict';

/* Controllers */

angular.module('app')
    .controller('DenunciasCtrl', ['$scope', '$state','$filter','$base64', '$http', function($scope, $state, $filter, $base64, $http) {
        $scope.getDenuncias = function() {
            $http.get('http://192.198.90.26:80/denuncia/get').success(function(result) {
                console.log(result.object);
                $scope.denuncias = result.object;
            });
        };

        $scope.getDenuncias();

        var table = $('#basicTable');
        
        $scope.options = {
            "sDom": "t",
            
            "destroy": true,
            "paging": false,
            "scrollCollapse": true,
            "aoColumnDefs": [{
                'bSortable': false,
                'aTargets': [0]
            }],
            "order": [
                [1, "desc"]
            ]
        };

        $scope.selectRow = function(event) {
            var element = event.currentTarget;
                if ($(element).is(':checked')) {
                $(element).closest('tr').addClass('selected');
            } else {
                $(element).closest('tr').removeClass('selected');
            }
        }

        $scope.openModal = function(publicacao) {
            $scope.publicacaoModal = publicacao;
            $scope.publicacaoModal.urlImg = 'http://urmusic.me:82/publicacao/' + publicacao.codigo + '.jpg';
            $('#meuModal').modal('show');
        }

        $scope.openModalAprovacao = function() {
            $('#modalAprovacao').modal('show');
        }

        $scope.closeModal = function() {
            $('#meuModal').modal('hide');
        }

        $scope.filtrarNumero = function(numero) {
            var nu = String(numero);
            if (nu.length <= 4) {
                return $filter('number')(nu);
            } else {
                return $filter('limitTo')(nu,nu.length - 3) + 'K';
            }
        };

        $scope.aprovarDenuncias = function(tipoAprovacao) {
            var denuncias = [];
            $scope.denuncias.forEach(function(denuncia) {
                if(denuncia.selected) {
                    $http.get('http://192.198.90.26:84/denuncia/aprovar/' + denuncia.codigo + '/' + tipoAprovacao).success(function(result) {

                        console.log(result.message);
                    });
                }
            }, this);
            $scope.getDenuncias();
        }
    }]);
