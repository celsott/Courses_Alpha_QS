(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('SalaMySuffixController', SalaMySuffixController);

    SalaMySuffixController.$inject = ['$scope', '$state', 'Sala'];

    function SalaMySuffixController ($scope, $state, Sala) {
        var vm = this;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        vm.salas = [];

        loadAll();

        function loadAll() {
            Sala.query(function(result) {
                vm.salas = result;
            });
        }
    }
})();
