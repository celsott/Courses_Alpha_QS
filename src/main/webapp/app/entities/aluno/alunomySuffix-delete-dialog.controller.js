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
