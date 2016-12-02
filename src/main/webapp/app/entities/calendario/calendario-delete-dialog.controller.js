(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('CalendarioDeleteController',CalendarioDeleteController);

    CalendarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Calendario'];

    function CalendarioDeleteController($uibModalInstance, entity, Calendario) {
        var vm = this;

        vm.calendario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Calendario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
