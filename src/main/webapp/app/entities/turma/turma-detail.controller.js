(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('TurmaDetailController', TurmaDetailController);

    TurmaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Turma', 'Disciplina', 'Professor', 'Aluno', 'Sala'];

    function TurmaDetailController($scope, $rootScope, $stateParams, previousState, entity, Turma, Disciplina, Professor, Aluno, Sala) {
        var vm = this;

        vm.turma = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:turmaUpdate', function(event, result) {
            vm.turma = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
