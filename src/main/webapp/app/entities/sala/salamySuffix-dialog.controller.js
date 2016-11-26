(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('SalaMySuffixDialogController', SalaMySuffixDialogController);

    SalaMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sala', 'Turma'];

    function SalaMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sala, Turma) {
        var vm = this;

        vm.sala = entity;
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
            if (vm.sala.id !== null) {
                Sala.update(vm.sala, onSaveSuccess, onSaveError);
            } else {
                Sala.save(vm.sala, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coursesAlphaQsApp:salaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
