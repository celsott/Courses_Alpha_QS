(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('DisciplinaMySuffixDialogController', DisciplinaMySuffixDialogController);

    DisciplinaMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Disciplina', 'Turma'];

    function DisciplinaMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Disciplina, Turma) {
        var vm = this;

        vm.disciplina = entity;
        vm.clear = clear;
        vm.save = save;
        vm.turmas = Turma.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.disciplina.id !== null) {
                Disciplina.update(vm.disciplina, onSaveSuccess, onSaveError);
            } else {
                Disciplina.save(vm.disciplina, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coursesAlphaQsApp:disciplinaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
