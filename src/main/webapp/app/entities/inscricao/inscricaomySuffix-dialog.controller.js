(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('InscricaoMySuffixDialogController', InscricaoMySuffixDialogController);

    InscricaoMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Inscricao', 'Turma', 'Aluno'];

    function InscricaoMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Inscricao, Turma, Aluno) {
        var vm = this;

        vm.inscricao = entity;
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
            if (vm.inscricao.id !== null) {
                Inscricao.update(vm.inscricao, onSaveSuccess, onSaveError);
            } else {
                Inscricao.save(vm.inscricao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coursesAlphaQsApp:inscricaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
