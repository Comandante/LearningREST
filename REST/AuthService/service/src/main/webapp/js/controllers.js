'use strict';

angular.module('authServiceAdmin.controllers', ['ui.bootstrap'])
    .controller('MainCtrl', ['$scope', '$rootScope', 'Token', 'Session', 'AUTH_EVENTS',
        function ($scope, $rootScope, Auth, Session, AUTH_EVENTS) {

            $scope.setCurrentAuth = function (auth) {
                $rootScope.currentAuth = auth;
                Session.auth = $rootScope.currentAuth;
            };
            $scope.login = function (credentials) {
                Auth.login(credentials).then(function (response) {
                    $scope.setCurrentAuth(response);
                    $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
                }, function (response) {
                    $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
                })
            };
            $scope.logout = function () {
                Auth.logout();
                $scope.setCurrentAuth(null);
            };
            $scope.register = function (credentials) {
                Auth.register(credentials).then(function (response) {
                    $scope.setCurrentAuth(response);
                    $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
                }, function (response) {
                    $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
                })
            };
        }])
    .controller('AuthsListCtrl', ['$scope', '$modal', 'Token',
        function ($scope, $modal, Auth) {
            $scope.totalPages = 0;
            $scope.authsCount = 0;
            $scope.headers = [
                {
                    title: 'Email',
                    value: 'email'
                },
                {
                    title: 'Login',
                    value: 'login'
                },
                {
                    title: 'First Name',
                    value: 'firstName'
                },
                {
                    title: 'Last Name',
                    value: 'lastName'
                },
                {
                    title: 'Gender',
                    value: 'gender'
                },
                {
                    title: 'Birthday',
                    value: 'birthDate'
                },
                {
                    title: 'Registration',
                    value: 'createDate'
                }
            ];

            $scope.filterCriteria = {
                page: 1,
                sortDir: 'ASC',
                sortedBy: 'email'
            };

            //The function that is responsible of fetching the result from the server and setting the grid to the new result
            $scope.fetchResult = function () {
                return Auth.page.search($scope.filterCriteria).then(function (response) {
                    $scope.auths = response.content;
                    $scope.totalPages = Math.ceil(response.total / response.size);
                    $scope.authsCount = response.total;
                }, function () {
                    $scope.auths = [];
                    $scope.totalPages = 0;
                    $scope.authsCount = 0;
                });
            };

            //called when navigate to another page in the pagination
            $scope.selectPage = function (page) {
                $scope.filterCriteria.page = page;
                $scope.fetchResult();
            };

            //Will be called when filtering the grid, will reset the page number to one
            $scope.filterResult = function () {
                $scope.filterCriteria.page = 1;
                $scope.fetchResult().then(function () {
                    //The request fires correctly but sometimes the ui doesn't update, that's a fix
                    $scope.filterCriteria.page = 1;
                });
            };

            //call back function that we passed to our custom directive sortBy, will be called when clicking on any field to sort
            $scope.onSort = function (sortedBy, sortDir) {
                $scope.filterCriteria.sortDir = sortDir;
                $scope.filterCriteria.sortedBy = sortedBy;
                $scope.filterCriteria.page = 1;
                $scope.fetchResult().then(function () {
                    //The request fires correctly but sometimes the ui doesn't update, that's a fix
                    $scope.filterCriteria.page = 1;
                });
            };

            //manually select a page to trigger an ajax request to populate the grid on page load
            $scope.selectPage(1);

            $scope.open = function (size, email) {

                var modalInstance = $modal.open({
                    templateUrl: 'partials/auths/details.html',
                    controller: 'AuthDetailsModalCtrl',
                    backdrop: true,
                    size: size,
                    resolve: {
                        email: function () {
                            return email;
                        }
                    }
                });

                modalInstance.result.then(function (auth) {
                    $scope.newAuth = auth;
                }, function () {
                });
            };
        }])
    .controller('AuthDetailsModalCtrl', ['$scope', '$modalInstance', 'email', 'Token',
        function ($scope, $modalInstance, email, Auth) {
            Auth.get(email).then(function (response) {
                $scope.auth = response;
            }, function (response) {
            });

            $scope.ok = function () {
                $modalInstance.close($scope.auth);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
        }])
    .controller('AuthDetailsCtrl', ['$scope', '$stateParams', 'Token',
        function ($scope, $stateParams, Auth) {
            Auth.get($stateParams.email).then(function (response) {
                $scope.auth = response;
            }, function (response) {
            });
        }])
    .controller('ClientsListCtrl', ['$scope', '$modal', '$stateParams', 'Client',
        function ($scope, $modal, $stateParams, Client) {
            $scope.totalPages = 0;
            $scope.clientsCount = 0;
            $scope.headers = [
                {
                    title: 'ClientID',
                    value: 'clientID'
                },
                {
                    title: 'Application Name',
                    value: 'applicationName'
                },
                {
                    title: 'URI',
                    value: 'applicationWebUri'
                },
                {
                    title: 'Last Updated By',
                    value: 'updatedBy'
                },
                {
                    title: 'Update Date',
                    value: 'updateDate'
                },
                {
                    title: 'Created By',
                    value: 'createdBy'
                },
                {
                    title: 'Create Date',
                    value: 'createDate'
                }
            ];

            $scope.filterCriteria = {
                page: 1,
                sortDir: 'ASC',
                sortedBy: 'applicationName'
            };

            //The function that is responsible of fetching the result from the server and setting the grid to the new result
            $scope.fetchResult = function () {
                return Client.page.search($scope.filterCriteria).then(function (response) {
                    $scope.clients = response.content;
                    $scope.totalPages = Math.ceil(response.total / response.size);
                    $scope.clientsCount = response.total;
                }, function () {
                    $scope.clients = [];
                    $scope.totalPages = 0;
                    $scope.clientsCount = 0;
                });
            };

            //called when navigate to another page in the pagination
            $scope.selectPage = function (page) {
                $scope.filterCriteria.page = page;
                $scope.fetchResult();
            };

            //Will be called when filtering the grid, will reset the page number to one
            $scope.filterResult = function () {
                $scope.filterCriteria.page = 1;
                $scope.fetchResult().then(function () {
                    //The request fires correctly but sometimes the ui doesn't update, that's a fix
                    $scope.filterCriteria.page = 1;
                });
            };

            //call back function that we passed to our custom directive sortBy, will be called when clicking on any field to sort
            $scope.onSort = function (sortedBy, sortDir) {
                $scope.filterCriteria.sortDir = sortDir;
                $scope.filterCriteria.sortedBy = sortedBy;
                $scope.filterCriteria.page = 1;
                $scope.fetchResult().then(function () {
                    //The request fires correctly but sometimes the ui doesn't update, that's a fix
                    $scope.filterCriteria.page = 1;
                });
            };
            $scope.remove = function (client) {
                Client.delete(client).then(function (response) {
                    $scope.fetchResult();
                }, function (response) {
                });
            };

            //manually select a page to trigger an ajax request to populate the grid on page load
            $scope.selectPage(1);
        }])
    .controller('ClientDetailsCtrl', ['$scope', '$stateParams', 'Client',
        function ($scope, $stateParams, Client) {
            $scope.mode = $stateParams.mode;

            $scope.roles = ['reader', 'writer', 'admin'];
            $scope.client = {
                applicationName: '',
                clientSecret: '',
                applicationDescription: '',
                applicationWebUri: '',
                redirectUris: [],
                allowedGrantTypes: []
            };
            $scope.original = angular.copy($scope.client);

            Client.get($stateParams.clientID).then(function (response) {
                if (response) {
                    $scope.client = response;
                    $scope.original = angular.copy($scope.client)
                }
            }, function () {
            });

            $scope.discard = function () {
                $scope.client = angular.copy($scope.original);
            };

            $scope.save = function () {
                $scope.original = angular.copy($scope.client);
                switch ($scope.mode) {
                    case 'new' :
                        Client.add($scope.client).then(function (response) {

                        }, function (response) {

                        });
                        break;
                    case 'edit' :
                        $scope.client.save();
                        break;
                }
                $scope.cancel();
            };

            $scope.addRole = function (role) {
                if ($scope.client.allowedGrantTypes.indexOf(role) == -1) {
                    $scope.client.allowedGrantTypes.push(role);
                }
            };

            $scope.isCancelDisabled = function () {
                return angular.equals($scope.original, $scope.client);
            };

            $scope.isSaveDisabled = function () {
                return $scope.myForm.$invalid || angular.equals($scope.original, $scope.client);
            };

            $scope.discard();
        }]);