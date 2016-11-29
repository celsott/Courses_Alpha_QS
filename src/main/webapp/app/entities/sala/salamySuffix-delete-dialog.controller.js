(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('SalaMySuffixDeleteController',SalaMySuffixDeleteController);

    SalaMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sala'];

    function SalaMySuffixDeleteController($uibModalInstance, entity, Sala) {
        var vm = this;

        vm.sala = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sala.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
