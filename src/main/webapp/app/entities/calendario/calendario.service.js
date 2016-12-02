(function() {
    'use strict';
    angular
        .module('coursesAlphaQsApp')
        .factory('Calendario', Calendario);

    Calendario.$inject = ['$resource', 'DateUtils'];

    function Calendario ($resource, DateUtils) {
        var resourceUrl =  'api/calendarios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataInicio = DateUtils.convertLocalDateFromServer(data.dataInicio);
                        data.dataFim = DateUtils.convertLocalDateFromServer(data.dataFim);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataInicio = DateUtils.convertLocalDateToServer(copy.dataInicio);
                    copy.dataFim = DateUtils.convertLocalDateToServer(copy.dataFim);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataInicio = DateUtils.convertLocalDateToServer(copy.dataInicio);
                    copy.dataFim = DateUtils.convertLocalDateToServer(copy.dataFim);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
