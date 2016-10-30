(function () {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
