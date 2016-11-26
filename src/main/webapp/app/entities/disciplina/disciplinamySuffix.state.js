(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('disciplinamySuffix', {
            parent: 'entity',
            url: '/disciplinamySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.disciplina.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disciplina/disciplinasmySuffix.html',
                    controller: 'DisciplinaMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('disciplina');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('disciplinamySuffix-detail', {
            parent: 'entity',
            url: '/disciplinamySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.disciplina.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disciplina/disciplinamySuffix-detail.html',
                    controller: 'DisciplinaMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('disciplina');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Disciplina', function($stateParams, Disciplina) {
                    return Disciplina.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'disciplinamySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('disciplinamySuffix-detail.edit', {
            parent: 'disciplinamySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplinamySuffix-dialog.html',
                    controller: 'DisciplinaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Disciplina', function(Disciplina) {
                            return Disciplina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disciplinamySuffix.new', {
            parent: 'disciplinamySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplinamySuffix-dialog.html',
                    controller: 'DisciplinaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codigo: null,
                                nome: null,
                                creditos: null,
                                cargaHoraria: null,
                                ementa: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('disciplinamySuffix', null, { reload: 'disciplinamySuffix' });
                }, function() {
                    $state.go('disciplinamySuffix');
                });
            }]
        })
        .state('disciplinamySuffix.edit', {
            parent: 'disciplinamySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplinamySuffix-dialog.html',
                    controller: 'DisciplinaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Disciplina', function(Disciplina) {
                            return Disciplina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disciplinamySuffix', null, { reload: 'disciplinamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disciplinamySuffix.delete', {
            parent: 'disciplinamySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disciplina/disciplinamySuffix-delete-dialog.html',
                    controller: 'DisciplinaMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Disciplina', function(Disciplina) {
                            return Disciplina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disciplinamySuffix', null, { reload: 'disciplinamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
