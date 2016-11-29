(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AlunoMySuffixController', AlunoMySuffixController);

    AlunoMySuffixController.$inject = ['$scope', '$state', 'Aluno'];

    function AlunoMySuffixController ($scope, $state, Aluno) {
        var vm = this;

        vm.alunos = [];

        loadAll();

        function loadAll() {
            Aluno.query(function(result) {
                vm.alunos = result;
            });
        }
    }
})();
