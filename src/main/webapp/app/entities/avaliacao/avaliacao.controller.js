(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AvaliacaoController', AvaliacaoController);

    AvaliacaoController.$inject = ['$scope', '$state', 'Avaliacao'];

    function AvaliacaoController ($scope, $state, Avaliacao) {
        var vm = this;
        
        vm.avaliacaos = [];

        loadAll();

        function loadAll() {
            Avaliacao.query(function(result) {
                vm.avaliacaos = result;
            });
        }
    }
})();
