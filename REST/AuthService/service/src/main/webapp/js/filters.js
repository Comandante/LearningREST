'use strict';

angular.module('authServiceAdmin.filters', [])

    .filter("exclude", function () {
        return function (input, exclude) {
            if (!exclude) return input;
            return input.filter(function(item) {
                return exclude.indexOf(item) === -1;
            });
        };
    });