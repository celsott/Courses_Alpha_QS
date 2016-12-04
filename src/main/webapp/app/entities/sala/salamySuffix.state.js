(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('salamySuffix', {
            parent: 'entity',
            url: '/salamySuffix',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA'],
                pageTitle: 'coursesAlphaQsApp.sala.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sala/salasmySuffix.html',
                    controller: 'SalaMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sala');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('salamySuffix-detail', {
            parent: 'entity',
            url: '/salamySuffix/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA'],
                pageTitle: 'coursesAlphaQsApp.sala.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sala/salamySuffix-detail.html',
                    controller: 'SalaMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sala');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sala', function($stateParams, Sala) {
                    return Sala.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'salamySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('salamySuffix-detail.edit', {
            parent: 'salamySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/salamySuffix-dialog.html',
                    controller: 'SalaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sala', function(Sala) {
                            return Sala.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salamySuffix.new', {
            parent: 'salamySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/salamySuffix-dialog.html',
                    controller: 'SalaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                capacidade: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('salamySuffix', null, { reload: 'salamySuffix' });
                }, function() {
                    $state.go('salamySuffix');
                });
            }]
        })
        .state('salamySuffix.edit', {
            parent: 'salamySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/salamySuffix-dialog.html',
                    controller: 'SalaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sala', function(Sala) {
                            return Sala.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salamySuffix', null, { reload: 'salamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salamySuffix.delete', {
            parent: 'salamySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sala/salamySuffix-delete-dialog.html',
                    controller: 'SalaMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sala', function(Sala) {
                            return Sala.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salamySuffix', null, { reload: 'salamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
