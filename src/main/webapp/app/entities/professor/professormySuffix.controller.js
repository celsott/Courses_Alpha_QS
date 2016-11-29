(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('ProfessorMySuffixController', ProfessorMySuffixController);

    ProfessorMySuffixController.$inject = ['$scope', '$state', 'Professor'];

    function ProfessorMySuffixController ($scope, $state, Professor) {
        var vm = this;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        vm.professors = [];

        loadAll();

        function loadAll() {
            Professor.query(function(result) {
                vm.professors = result;
            });
        }
    }
})();
