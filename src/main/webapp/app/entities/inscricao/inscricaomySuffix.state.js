(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('inscricaomySuffix', {
            parent: 'entity',
            url: '/inscricaomySuffix',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA', 'ROLE_PROFESSOR'],
                pageTitle: 'coursesAlphaQsApp.inscricao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inscricao/inscricaosmySuffix.html',
                    controller: 'InscricaoMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inscricao');
                    $translatePartialLoader.addPart('situacao');
                    $translatePartialLoader.addPart('nota');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('inscricaomySuffix-detail', {
            parent: 'entity',
            url: '/inscricaomySuffix/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA', 'ROLE_PROFESSOR'],
                pageTitle: 'coursesAlphaQsApp.inscricao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inscricao/inscricaomySuffix-detail.html',
                    controller: 'InscricaoMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inscricao');
                    $translatePartialLoader.addPart('situacao');
                    $translatePartialLoader.addPart('nota');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Inscricao', function($stateParams, Inscricao) {
                    return Inscricao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'inscricaomySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('inscricaomySuffix-detail.edit', {
            parent: 'inscricaomySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA', 'ROLE_PROFESSOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricaomySuffix-dialog.html',
                    controller: 'InscricaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inscricaomySuffix.new', {
            parent: 'inscricaomySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricaomySuffix-dialog.html',
                    controller: 'InscricaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                situacao: null,
                                nota: null,
                                frequencia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('inscricaomySuffix', null, { reload: 'inscricaomySuffix' });
                }, function() {
                    $state.go('inscricaomySuffix');
                });
            }]
        })
        .state('inscricaomySuffix.edit', {
            parent: 'inscricaomySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA', 'ROLE_PROFESSOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricaomySuffix-dialog.html',
                    controller: 'InscricaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inscricaomySuffix', null, { reload: 'inscricaomySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inscricaomySuffix.delete', {
            parent: 'inscricaomySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inscricao/inscricaomySuffix-delete-dialog.html',
                    controller: 'InscricaoMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Inscricao', function(Inscricao) {
                            return Inscricao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inscricaomySuffix', null, { reload: 'inscricaomySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
