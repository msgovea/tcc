'use strict';

/* Controllers */

angular.module('app')
    // Chart controller 
    .controller('ConfirmaCadastroCtrl', ['$scope', function($scope) {
        $scope.modal = {};
        $scope.modal.slideUp = "default";
        $scope.modal.stickUp = "default";

        $scope.toggleSlideUpSize = function() {
            var size = $scope.modal.slideUp;
            var modalElem = $('#modalSlideUp');
            if (size == "mini") {
                $('#modalSlideUpSmall').modal('show')
            } else {
                $('#modalSlideUp').modal('show')
                if (size == "default") {
                    modalElem.children('.modal-dialog').removeClass('modal-lg');
                } else if (size == "full") {
                    modalElem.children('.modal-dialog').addClass('modal-lg');
                }
            }
        };

    }]);