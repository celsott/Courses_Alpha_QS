(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('professormySuffix', {
            parent: 'entity',
            url: '/professormySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.professor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/professor/professorsmySuffix.html',
                    controller: 'ProfessorMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('professor');
                    $translatePartialLoader.addPart('sexo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('professormySuffix-detail', {
            parent: 'entity',
            url: '/professormySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coursesAlphaQsApp.professor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/professor/professormySuffix-detail.html',
                    controller: 'ProfessorMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('professor');
                    $translatePartialLoader.addPart('sexo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Professor', function($stateParams, Professor) {
                    return Professor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'professormySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('professormySuffix-detail.edit', {
            parent: 'professormySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/professor/professormySuffix-dialog.html',
                    controller: 'ProfessorMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Professor', function(Professor) {
                            return Professor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('professormySuffix.new', {
            parent: 'professormySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/professor/professormySuffix-dialog.html',
                    controller: 'ProfessorMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
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
                    $state.go('professormySuffix', null, { reload: 'professormySuffix' });
                }, function() {
                    $state.go('professormySuffix');
                });
            }]
        })
        .state('professormySuffix.edit', {
            parent: 'professormySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/professor/professormySuffix-dialog.html',
                    controller: 'ProfessorMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Professor', function(Professor) {
                            return Professor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('professormySuffix', null, { reload: 'professormySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('professormySuffix.delete', {
            parent: 'professormySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/professor/professormySuffix-delete-dialog.html',
                    controller: 'ProfessorMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Professor', function(Professor) {
                            return Professor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('professormySuffix', null, { reload: 'professormySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
