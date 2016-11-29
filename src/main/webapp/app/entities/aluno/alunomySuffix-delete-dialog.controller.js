(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AlunoMySuffixDeleteController',AlunoMySuffixDeleteController);

    AlunoMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Aluno'];

    function AlunoMySuffixDeleteController($uibModalInstance, entity, Aluno) {
        var vm = this;

        vm.aluno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Aluno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
