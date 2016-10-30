(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AvaliacaoDetailController', AvaliacaoDetailController);

    AvaliacaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Avaliacao', 'Aluno', 'Disciplina'];

    function AvaliacaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Avaliacao, Aluno, Disciplina) {
        var vm = this;

        vm.avaliacao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:avaliacaoUpdate', function(event, result) {
            vm.avaliacao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
