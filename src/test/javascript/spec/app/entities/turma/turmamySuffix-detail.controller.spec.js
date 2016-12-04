'use strict';

describe('Controller Tests', function() {

    describe('Turma Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTurma, MockInscricao, MockProfessor, MockDisciplina, MockSala;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTurma = jasmine.createSpy('MockTurma');
            MockInscricao = jasmine.createSpy('MockInscricao');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockDisciplina = jasmine.createSpy('MockDisciplina');
            MockSala = jasmine.createSpy('MockSala');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Turma': MockTurma,
                'Inscricao': MockInscricao,
                'Professor': MockProfessor,
                'Disciplina': MockDisciplina,
                'Sala': MockSala
            };
            createController = function() {
                $injector.get('$controller')("TurmaMySuffixDetailController", locals);
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
