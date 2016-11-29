(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('DisciplinaMySuffixDetailController', DisciplinaMySuffixDetailController);

    DisciplinaMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Disciplina', 'Turma'];

    function DisciplinaMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Disciplina, Turma) {
        var vm = this;

        vm.disciplina = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:disciplinaUpdate', function(event, result) {
            vm.disciplina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
