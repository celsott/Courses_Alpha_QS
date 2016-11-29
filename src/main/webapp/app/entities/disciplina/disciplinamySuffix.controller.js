(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('DisciplinaMySuffixController', DisciplinaMySuffixController);

    DisciplinaMySuffixController.$inject = ['$scope', '$state', 'Disciplina'];

    function DisciplinaMySuffixController ($scope, $state, Disciplina) {
        var vm = this;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        vm.disciplinas = [];

        loadAll();

        function loadAll() {
            Disciplina.query(function(result) {
                vm.disciplinas = result;
            });
        }
    }
})();
