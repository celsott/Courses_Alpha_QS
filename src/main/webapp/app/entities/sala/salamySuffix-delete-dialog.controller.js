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
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
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
