(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(compileServiceConfig);

    compileServiceConfig.$inject = ['$compileProvider','DEBUG_INFO_ENABLED'];

    function compileServiceConfig($compileProvider,DEBUG_INFO_ENABLED) {
        // disable debug data on prod profile to improve performance
        $compileProvider.debugInfoEnabled(DEBUG_INFO_ENABLED);
<<<<<<< HEAD

=======
        
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
        /*
        If you wish to debug an application with this information
        then you should open up a debug console in the browser
        then call this method directly in this console:

		angular.reloadWithDebugInfo();
		*/
    }
})();
