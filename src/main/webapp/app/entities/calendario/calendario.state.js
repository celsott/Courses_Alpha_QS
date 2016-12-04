(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('calendario', {
            parent: 'entity',
            url: '/calendario',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA'],
                pageTitle: 'coursesAlphaQsApp.calendario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calendario/calendarios.html',
                    controller: 'CalendarioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('calendario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('calendario-detail', {
            parent: 'entity',
            url: '/calendario/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA'],
                pageTitle: 'coursesAlphaQsApp.calendario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calendario/calendario-detail.html',
                    controller: 'CalendarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('calendario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Calendario', function($stateParams, Calendario) {
                    return Calendario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'calendario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('calendario-detail.edit', {
            parent: 'calendario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendario/calendario-dialog.html',
                    controller: 'CalendarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Calendario', function(Calendario) {
                            return Calendario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calendario.new', {
            parent: 'calendario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendario/calendario-dialog.html',
                    controller: 'CalendarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                evento: null,
                                dataInicio: null,
                                dataFim: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('calendario', null, { reload: 'calendario' });
                }, function() {
                    $state.go('calendario');
                });
            }]
        })
        .state('calendario.edit', {
            parent: 'calendario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendario/calendario-dialog.html',
                    controller: 'CalendarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Calendario', function(Calendario) {
                            return Calendario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calendario', null, { reload: 'calendario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calendario.delete', {
            parent: 'calendario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendario/calendario-delete-dialog.html',
                    controller: 'CalendarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Calendario', function(Calendario) {
                            return Calendario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calendario', null, { reload: 'calendario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
