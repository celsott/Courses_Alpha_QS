(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('TurmaDialogController', TurmaDialogController);

    TurmaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Turma', 'Disciplina', 'Professor', 'Aluno', 'Sala'];

    function TurmaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Turma, Disciplina, Professor, Aluno, Sala) {
        var vm = this;

        vm.turma = entity;
        vm.clear = clear;
        vm.save = save;
        vm.disciplinas = Disciplina.query();
        vm.professors = Professor.query();
        vm.alunos = Aluno.query();
        vm.salas = Sala.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.turma.id !== null) {
                Turma.update(vm.turma, onSaveSuccess, onSaveError);
            } else {
                Turma.save(vm.turma, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coursesAlphaQsApp:turmaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
