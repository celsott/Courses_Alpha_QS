(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('ProfessorMySuffixDetailController', ProfessorMySuffixDetailController);

    ProfessorMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Professor', 'Turma'];

    function ProfessorMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Professor, Turma) {
        var vm = this;

        vm.professor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('coursesAlphaQsApp:professorUpdate', function(event, result) {
            vm.professor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
