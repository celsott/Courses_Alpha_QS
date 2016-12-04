(function() {
    'use strict';

    angular
        .module('coursesAlphaQsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('turmamySuffix', {
            parent: 'entity',
            url: '/turmamySuffix',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA'],
                pageTitle: 'coursesAlphaQsApp.turma.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/turma/turmasmySuffix.html',
                    controller: 'TurmaMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('turma');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('turmamySuffix-detail', {
            parent: 'entity',
            url: '/turmamySuffix/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA'],
                pageTitle: 'coursesAlphaQsApp.turma.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/turma/turmamySuffix-detail.html',
                    controller: 'TurmaMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('turma');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Turma', function($stateParams, Turma) {
                    return Turma.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'turmamySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('turmamySuffix-detail.edit', {
            parent: 'turmamySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turmamySuffix-dialog.html',
                    controller: 'TurmaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Turma', function(Turma) {
                            return Turma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('turmamySuffix.new', {
            parent: 'turmamySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turmamySuffix-dialog.html',
                    controller: 'TurmaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                horario: null,
                                periodo: null,
                                ano: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('turmamySuffix', null, { reload: 'turmamySuffix' });
                }, function() {
                    $state.go('turmamySuffix');
                });
            }]
        })
        .state('turmamySuffix.edit', {
            parent: 'turmamySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turmamySuffix-dialog.html',
                    controller: 'TurmaMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Turma', function(Turma) {
                            return Turma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('turmamySuffix', null, { reload: 'turmamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('turmamySuffix.delete', {
            parent: 'turmamySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_SECRETARIA']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turmamySuffix-delete-dialog.html',
                    controller: 'TurmaMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Turma', function(Turma) {
                            return Turma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('turmamySuffix', null, { reload: 'turmamySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
