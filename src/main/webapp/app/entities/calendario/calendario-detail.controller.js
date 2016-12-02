(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('CalendarioDetailController', CalendarioDetailController);

    CalendarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Calendario'];

    function CalendarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Calendario) {
        var vm = this;

        vm.calendario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:calendarioUpdate', function(event, result) {
            vm.calendario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
