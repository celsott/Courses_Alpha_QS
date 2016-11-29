(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AlunoMySuffixController', AlunoMySuffixController);

    AlunoMySuffixController.$inject = ['$scope', '$state', 'Aluno'];

    function AlunoMySuffixController ($scope, $state, Aluno) {
        var vm = this;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        vm.alunos = [];

        loadAll();

        function loadAll() {
            Aluno.query(function(result) {
                vm.alunos = result;
            });
        }
    }
})();
