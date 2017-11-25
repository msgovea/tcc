'use strict';

angular.module('app')

.controller('HotsiteCtrl', ['$scope', '$window', function($scope, $window) {
    $window.location.href = "https://urmusic.me";
}]);