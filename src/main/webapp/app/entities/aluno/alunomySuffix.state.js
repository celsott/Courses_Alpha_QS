(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alunomySuffix', {
            parent: 'entity',
            url: '/alunomySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.aluno.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aluno/alunosmySuffix.html',
                    controller: 'AlunoMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aluno');
                    $translatePartialLoader.addPart('sexo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('alunomySuffix-detail', {
            parent: 'entity',
            url: '/alunomySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.aluno.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aluno/alunomySuffix-detail.html',
                    controller: 'AlunoMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aluno');
                    $translatePartialLoader.addPart('sexo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Aluno', function($stateParams, Aluno) {
                    return Aluno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alunomySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alunomySuffix-detail.edit', {
            parent: 'alunomySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/alunomySuffix-dialog.html',
                    controller: 'AlunoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alunomySuffix.new', {
            parent: 'alunomySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/alunomySuffix-dialog.html',
                    controller: 'AlunoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dre: null,
                                nome: null,
                                sobrenome: null,
                                cpf: null,
                                dataNascimento: null,
                                sexo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alunomySuffix', null, { reload: 'alunomySuffix' });
                }, function() {
                    $state.go('alunomySuffix');
                });
            }]
        })
        .state('alunomySuffix.edit', {
            parent: 'alunomySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/alunomySuffix-dialog.html',
                    controller: 'AlunoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alunomySuffix', null, { reload: 'alunomySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alunomySuffix.delete', {
            parent: 'alunomySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/alunomySuffix-delete-dialog.html',
                    controller: 'AlunoMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alunomySuffix', null, { reload: 'alunomySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
