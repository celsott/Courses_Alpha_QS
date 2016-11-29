(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('DisciplinaMySuffixController', DisciplinaMySuffixController);

    DisciplinaMySuffixController.$inject = ['$scope', '$state', 'Disciplina'];

    function DisciplinaMySuffixController ($scope, $state, Disciplina) {
        var vm = this;

        vm.disciplinas = [];

        loadAll();

        function loadAll() {
            Disciplina.query(function(result) {
                vm.disciplinas = result;
            });
        }
    }
})();
