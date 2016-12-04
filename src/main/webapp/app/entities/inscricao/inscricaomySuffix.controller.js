(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .controller('InscricaoMySuffixController', InscricaoMySuffixController);

    InscricaoMySuffixController.$inject = ['$scope', '$state', 'Inscricao'];

    function InscricaoMySuffixController ($scope, $state, Inscricao) {
        var vm = this;

        vm.inscricaos = [];

        loadAll();

        function loadAll() {
            Inscricao.query(function(result) {
                vm.inscricaos = result;
            });
        }
    }
})();
