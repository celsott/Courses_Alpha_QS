(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .factory('translationStorageProvider', translationStorageProvider);

    translationStorageProvider.$inject = ['$cookies', '$log', 'LANGUAGES'];

    function translationStorageProvider($cookies, $log, LANGUAGES) {
        return {
            get: get,
            put: put
        };

        function get(name) {
            if (LANGUAGES.indexOf($cookies.getObject(name)) === -1) {
<<<<<<< HEAD
                $log.info('Resetting invalid cookie language "' + $cookies.getObject(name) + '" to preferred language "en"');
=======
                $log.info('Resetting invalid cookie language "' + $cookies.getObject(name) + '" to prefered language "en"');
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
                $cookies.putObject(name, 'en');
            }
            return $cookies.getObject(name);
        }

        function put(name, value) {
            $cookies.putObject(name, value);
        }
    }
})();
