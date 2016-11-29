(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('SalaMySuffixDetailController', SalaMySuffixDetailController);

    SalaMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sala', 'Turma'];

    function SalaMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Sala, Turma) {
        var vm = this;

        vm.sala = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:salaUpdate', function(event, result) {
            vm.sala = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
