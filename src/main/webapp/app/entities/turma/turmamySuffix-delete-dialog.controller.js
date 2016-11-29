(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('TurmaMySuffixDeleteController',TurmaMySuffixDeleteController);

    TurmaMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Turma'];

    function TurmaMySuffixDeleteController($uibModalInstance, entity, Turma) {
        var vm = this;

        vm.turma = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Turma.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
