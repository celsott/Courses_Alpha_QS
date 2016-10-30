(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('SalaController', SalaController);

    SalaController.$inject = ['$scope', '$state', 'Sala'];

    function SalaController ($scope, $state, Sala) {
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
