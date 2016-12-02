(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('CalendarioController', CalendarioController);

    CalendarioController.$inject = ['$scope', '$state', 'Calendario'];

    function CalendarioController ($scope, $state, Calendario) {
        var vm = this;
        
        vm.calendarios = [];

        loadAll();

        function loadAll() {
            Calendario.query(function(result) {
                vm.calendarios = result;
            });
        }
    }
})();
