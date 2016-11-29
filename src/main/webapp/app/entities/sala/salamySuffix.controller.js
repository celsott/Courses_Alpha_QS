(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('SalaMySuffixController', SalaMySuffixController);

    SalaMySuffixController.$inject = ['$scope', '$state', 'Sala'];

    function SalaMySuffixController ($scope, $state, Sala) {
        var vm = this;

        vm.salas = [];

        loadAll();

        function loadAll() {
            Sala.query(function(result) {
                vm.salas = result;
            });
        }
    }
})();
