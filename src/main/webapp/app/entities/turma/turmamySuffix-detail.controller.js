(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('TurmaMySuffixDetailController', TurmaMySuffixDetailController);

    TurmaMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Turma', 'Avaliacao', 'Professor', 'Disciplina', 'Sala'];

    function TurmaMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Turma, Avaliacao, Professor, Disciplina, Sala) {
        var vm = this;

        vm.turma = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:turmaUpdate', function(event, result) {
            vm.turma = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
