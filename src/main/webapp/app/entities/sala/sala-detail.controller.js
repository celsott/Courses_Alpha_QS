(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('SalaDetailController', SalaDetailController);

    SalaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sala'];

    function SalaDetailController($scope, $rootScope, $stateParams, previousState, entity, Sala) {
        var vm = this;

        vm.sala = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:salaUpdate', function(event, result) {
            vm.sala = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
