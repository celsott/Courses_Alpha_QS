(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AlunoMySuffixDetailController', AlunoMySuffixDetailController);

    AlunoMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aluno', 'Avaliacao'];

    function AlunoMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Aluno, Avaliacao) {
        var vm = this;

        vm.aluno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:alunoUpdate', function(event, result) {
            vm.aluno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
