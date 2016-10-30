'use strict';

describe('Controller Tests', function() {

    describe('Turma Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTurma, MockDisciplina, MockProfessor, MockAluno, MockSala;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTurma = jasmine.createSpy('MockTurma');
            MockDisciplina = jasmine.createSpy('MockDisciplina');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockAluno = jasmine.createSpy('MockAluno');
            MockSala = jasmine.createSpy('MockSala');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Turma': MockTurma,
                'Disciplina': MockDisciplina,
                'Professor': MockProfessor,
                'Aluno': MockAluno,
                'Sala': MockSala
            };
            createController = function() {
                $injector.get('$controller')("TurmaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coursesAlphaQsApp:turmaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
