(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp', [
<<<<<<< HEAD
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
=======
            'ngStorage', 
            'tmh.dynamicLocale',
            'pascalprecht.translate', 
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
