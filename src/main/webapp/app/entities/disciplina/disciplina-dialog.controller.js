(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('DisciplinaDialogController', DisciplinaDialogController);

    DisciplinaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Disciplina'];

    function DisciplinaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Disciplina) {
        var vm = this;

        vm.disciplina = entity;
        vm.clear = clear;
        vm.save = save;

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
