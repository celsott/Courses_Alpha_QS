(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('ProfessorMySuffixController', ProfessorMySuffixController);

    ProfessorMySuffixController.$inject = ['$scope', '$state', 'Professor'];

    function ProfessorMySuffixController ($scope, $state, Professor) {
        var vm = this;
        
        vm.professors = [];

        loadAll();

        function loadAll() {
            Professor.query(function(result) {
                vm.professors = result;
            });
        }
    }
})();
