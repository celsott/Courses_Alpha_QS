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
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
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
