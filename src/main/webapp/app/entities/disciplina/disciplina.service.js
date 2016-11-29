(function() {
    'use strict';
    angular
        .module('coursesAlphaQsApp')
        .factory('Disciplina', Disciplina);

    Disciplina.$inject = ['$resource'];

    function Disciplina ($resource) {
        var resourceUrl =  'api/disciplinas/:id';

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
