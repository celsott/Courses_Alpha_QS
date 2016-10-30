(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('DisciplinaDetailController', DisciplinaDetailController);

    DisciplinaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Disciplina'];

    function DisciplinaDetailController($scope, $rootScope, $stateParams, previousState, entity, Disciplina) {
        var vm = this;

        vm.disciplina = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:disciplinaUpdate', function(event, result) {
            vm.disciplina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
