(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AvaliacaoMySuffixDetailController', AvaliacaoMySuffixDetailController);

    AvaliacaoMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Avaliacao', 'Turma', 'Aluno'];

    function AvaliacaoMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Avaliacao, Turma, Aluno) {
        var vm = this;

        vm.avaliacao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:avaliacaoUpdate', function(event, result) {
            vm.avaliacao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
