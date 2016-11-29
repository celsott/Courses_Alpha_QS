(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('avaliacaomySuffix', {
            parent: 'entity',
            url: '/avaliacaomySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.avaliacao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao/avaliacaosmySuffix.html',
                    controller: 'AvaliacaoMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacao');
                    $translatePartialLoader.addPart('nota');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('avaliacaomySuffix-detail', {
            parent: 'entity',
            url: '/avaliacaomySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.avaliacao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avaliacao/avaliacaomySuffix-detail.html',
                    controller: 'AvaliacaoMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avaliacao');
                    $translatePartialLoader.addPart('nota');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Avaliacao', function($stateParams, Avaliacao) {
                    return Avaliacao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'avaliacaomySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('avaliacaomySuffix-detail.edit', {
            parent: 'avaliacaomySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacaomySuffix-dialog.html',
                    controller: 'AvaliacaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Avaliacao', function(Avaliacao) {
                            return Avaliacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacaomySuffix.new', {
            parent: 'avaliacaomySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacaomySuffix-dialog.html',
                    controller: 'AvaliacaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nota: null,
                                frequencia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('avaliacaomySuffix', null, { reload: 'avaliacaomySuffix' });
                }, function() {
                    $state.go('avaliacaomySuffix');
                });
            }]
        })
        .state('avaliacaomySuffix.edit', {
            parent: 'avaliacaomySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacaomySuffix-dialog.html',
                    controller: 'AvaliacaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Avaliacao', function(Avaliacao) {
                            return Avaliacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacaomySuffix', null, { reload: 'avaliacaomySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avaliacaomySuffix.delete', {
            parent: 'avaliacaomySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avaliacao/avaliacaomySuffix-delete-dialog.html',
                    controller: 'AvaliacaoMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Avaliacao', function(Avaliacao) {
                            return Avaliacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avaliacaomySuffix', null, { reload: 'avaliacaomySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
