'use strict';

describe('Controller Tests', function() {

    describe('Avaliacao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAvaliacao, MockAluno, MockDisciplina;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAvaliacao = jasmine.createSpy('MockAvaliacao');
            MockAluno = jasmine.createSpy('MockAluno');
            MockDisciplina = jasmine.createSpy('MockDisciplina');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Avaliacao': MockAvaliacao,
                'Aluno': MockAluno,
                'Disciplina': MockDisciplina
            };
            createController = function() {
                $injector.get('$controller')("AvaliacaoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coursesAlphaQsApp:avaliacaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
