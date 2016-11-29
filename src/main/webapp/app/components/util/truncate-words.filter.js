(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .filter('words', words);

    function words() {
        return wordsFilter;

        function wordsFilter(input, words) {
            if (isNaN(words)) {
                return input;
            }
            if (words <= 0) {
                return '';
            }
            if (input) {
                var inputWords = input.split(/\s+/);
                if (inputWords.length > words) {
                    input = inputWords.slice(0, words).join(' ') + '...';
                }
            }
<<<<<<< HEAD
=======
            
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
            return input;
        }
    }
})();
