(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('CalendarioDialogController', CalendarioDialogController);

    CalendarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Calendario'];

    function CalendarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Calendario) {
        var vm = this;

        vm.calendario = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.calendario.id !== null) {
                Calendario.update(vm.calendario, onSaveSuccess, onSaveError);
            } else {
                Calendario.save(vm.calendario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coursesAlphaQsApp:calendarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataInicio = false;
        vm.datePickerOpenStatus.dataFim = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
