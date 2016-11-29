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
