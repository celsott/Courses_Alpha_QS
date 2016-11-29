(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('DisciplinaMySuffixDeleteController',DisciplinaMySuffixDeleteController);

    DisciplinaMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Disciplina'];

    function DisciplinaMySuffixDeleteController($uibModalInstance, entity, Disciplina) {
        var vm = this;

        vm.disciplina = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Disciplina.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
