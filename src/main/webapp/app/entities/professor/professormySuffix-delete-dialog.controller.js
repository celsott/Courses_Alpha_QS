(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('ProfessorMySuffixDeleteController',ProfessorMySuffixDeleteController);

    ProfessorMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Professor'];

    function ProfessorMySuffixDeleteController($uibModalInstance, entity, Professor) {
        var vm = this;

        vm.professor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Professor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
