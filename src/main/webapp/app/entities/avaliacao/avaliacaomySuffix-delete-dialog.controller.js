(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AvaliacaoMySuffixDeleteController',AvaliacaoMySuffixDeleteController);

    AvaliacaoMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Avaliacao'];

    function AvaliacaoMySuffixDeleteController($uibModalInstance, entity, Avaliacao) {
        var vm = this;

        vm.avaliacao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Avaliacao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
