(function() {
    'use strict';
    angular
        .module('coursesAlphaQsApp')
        .factory('Inscricao', Inscricao);

    Inscricao.$inject = ['$resource'];

    function Inscricao ($resource) {
        var resourceUrl =  'api/inscricaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
