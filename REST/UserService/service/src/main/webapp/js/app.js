'use strict';

var userServiceAdmin = angular.module('userServiceAdmin', [
    //'ngRoute',
    'ngCookies',
    'userServiceAdmin.controllers',
    'userServiceAdmin.services',
    'userServiceAdmin.directives',
    'restangular',
    'ngAnimate',
    'ui.router'
    //'route-segment',
    //'view-segment'
])
    .constant('AUTH_EVENTS', {
        loginSuccess: 'auth-login-success',
        loginFailed: 'auth-login-failed',
        logoutSuccess: 'auth-logout-success',
        sessionTimeout: 'auth-session-timeout',
        notAuthenticated: 'auth-not-authenticated',
        notAuthorized: 'auth-not-authorized'
    })
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', 'RestangularProvider',
        function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider, RestangularProvider) {

            RestangularProvider.setBaseUrl('service/rest/v1/');

            var access = routingConfig.accessLevels;

            $urlRouterProvider
                .when('', '/users/')
                .when('/login', '/login.html');

            $stateProvider
                .state('users', {
                    abstract: true,
                    url: '/users',
                    template: '<ui-view/>',
                    controller: 'MainCtrl',
                    data: {
                        access: access.user
                    }
                })
                .state('users.list', {
                    url: '/',
                    templateUrl: 'partials/users/list.html',
                    controller: 'UsersListCtrl'
                })
                .state('users.details', {
                    url: '/:email/:mode',
                    templateUrl: 'partials/users/details.html',
                    controller: 'UserDetailsCtrl',
                    data: {
                        access: access.admin
                    }
                });

            // FIX for trailing slashes. Gracefully "borrowed" from https://github.com/angular-ui/ui-router/issues/50
            $urlRouterProvider.rule(function ($injector, $location) {
                if ($location.protocol() === 'file')
                    return;

                var path = $location.path()
                // Note: misnomer. This returns a query object, not a search string
                    , search = $location.search(), params
                    ;

                // check to see if the path already ends in '/'
                if (path[path.length - 1] === '/') {
                    return;
                }

                // If there was no search string / query params, return with a `/`
                if (Object.keys(search).length === 0) {
                    return path + '/';
                }

                // Otherwise build the search string and return a `/?` prefix
                params = [];
                angular.forEach(search, function (v, k) {
                    params.push(k + '=' + v);
                });
                return path + '/?' + params.join('&');
            });

            //$locationProvider.html5Mode(true);

            $httpProvider.interceptors.push(function ($q, $location) {
                return {
                    'responseError': function (response) {
                        if (response.status === 401 || response.status === 403) {
                            $location.path('/users');
                            return $q.reject(response);
                        }
                        else {
                            return $q.reject(response);
                        }
                    }
                }
            });
        }])
    .run(['$rootScope', '$state', 'Auth', function ($rootScope, $state, Auth) {
        $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
            if (!Auth.authorize(toState.data.access)) {
                $rootScope.error = "Seems like you tried accessing a route you don't have access to...";
                event.preventDefault();

                if (fromState.url === '^') {
                    if (Auth.isLoggedIn())
                        $state.go('users');
                    else {
                        $rootScope.error = null;
                        $state.go('login');
                    }
                }
            }
        });

    }]);