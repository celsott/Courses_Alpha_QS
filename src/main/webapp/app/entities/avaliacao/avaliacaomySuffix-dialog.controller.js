(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AvaliacaoMySuffixDialogController', AvaliacaoMySuffixDialogController);

    AvaliacaoMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Avaliacao', 'Turma', 'Aluno'];

    function AvaliacaoMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Avaliacao, Turma, Aluno) {
        var vm = this;

        vm.avaliacao = entity;
        vm.clear = clear;
        vm.save = save;
        vm.turmas = Turma.query();
        vm.alunos = Aluno.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.avaliacao.id !== null) {
                Avaliacao.update(vm.avaliacao, onSaveSuccess, onSaveError);
            } else {
                Avaliacao.save(vm.avaliacao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coursesAlphaQsApp:avaliacaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
