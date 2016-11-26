(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('AvaliacaoMySuffixController', AvaliacaoMySuffixController);

    AvaliacaoMySuffixController.$inject = ['$scope', '$state', 'Avaliacao'];

    function AvaliacaoMySuffixController ($scope, $state, Avaliacao) {
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
