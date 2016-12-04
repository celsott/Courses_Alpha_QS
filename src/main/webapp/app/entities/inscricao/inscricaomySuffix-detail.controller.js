(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('InscricaoMySuffixDetailController', InscricaoMySuffixDetailController);

    InscricaoMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Inscricao', 'Turma', 'Aluno'];

    function InscricaoMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Inscricao, Turma, Aluno) {
        var vm = this;

        vm.inscricao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:inscricaoUpdate', function(event, result) {
            vm.inscricao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
