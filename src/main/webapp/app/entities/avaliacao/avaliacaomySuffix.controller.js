(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AvaliacaoMySuffixController', AvaliacaoMySuffixController);

    AvaliacaoMySuffixController.$inject = ['$scope', '$state', 'Avaliacao'];

    function AvaliacaoMySuffixController ($scope, $state, Avaliacao) {
        var vm = this;
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        vm.avaliacaos = [];

        loadAll();

        function loadAll() {
            Avaliacao.query(function(result) {
                vm.avaliacaos = result;
            });
        }
    }
})();
