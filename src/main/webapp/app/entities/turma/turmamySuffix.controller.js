(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('TurmaMySuffixController', TurmaMySuffixController);

    TurmaMySuffixController.$inject = ['$scope', '$state', 'Turma'];

    function TurmaMySuffixController ($scope, $state, Turma) {
        var vm = this;

        vm.turmas = [];

        loadAll();

        function loadAll() {
            Turma.query(function(result) {
                vm.turmas = result;
            });
        }
    }
})();
