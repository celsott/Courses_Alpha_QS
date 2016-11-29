(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('TurmaMySuffixController', TurmaMySuffixController);

    TurmaMySuffixController.$inject = ['$scope', '$state', 'Turma'];

    function TurmaMySuffixController ($scope, $state, Turma) {
        var vm = this;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        vm.turmas = [];

        loadAll();

        function loadAll() {
            Turma.query(function(result) {
                vm.turmas = result;
            });
        }
    }
})();
