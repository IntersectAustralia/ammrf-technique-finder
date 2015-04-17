package org.ammrf.tf.admin

import org.ammrf.tf.Science;
import org.ammrf.tf.OptionType;
import org.ammrf.tf.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import grails.test.*

class OptionControllerTests extends ControllerUnitTestCase {
	def options
	def optionService
	def science = Science.BIOLOGY
	def type = OptionType.LEFT
	
    protected void setUp() {
        super.setUp()
        options = []
        for(i in [2, 1, 3]) {
        	options += new Option(priority:i,
        			name: "Some test option $i", 
        			science: science, 
        			type: type)
        }
        mockDomain(Option, options)
        optionService = mockFor(OptionService)
        OptionController.metaClass.message = { Map args -> return args['code']}
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    private setScienceAndType(mockParams) {
    	mockParams.science = science.toString()
    	mockParams.type = type.toString()
    }

    void testMoveUp() {
    	optionService.demand.moveUp() {Option optionInstance -> }
    	controller.optionService = optionService.createMock()
    	
    	mockParams.id = options.find{it.priority == 2}.id
    	def model = controller.moveUp()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.movedUp.message', controller.flash.message
    }
    
    void testMoveDown() {
    	optionService.demand.moveDown() {Option optionInstance -> }
    	controller.optionService = optionService.createMock()
    	
    	mockParams.id = options.find{it.priority == 2}.id
    	def model = controller.moveDown()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.movedDown.message', controller.flash.message
    }
    
    void testMoveUpIllegal() {
    	optionService.demand.moveUp() {Option optionInstance -> throw new IllegalArgumentException()}
    	controller.optionService = optionService.createMock()
    	
    	mockParams.id = options.find{it.priority == 1}.id
    	def model = controller.moveUp()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.not.movedUp.message', controller.flash.message
    }
    
    void testMoveDownIllegal() {
    	optionService.demand.moveDown() {Option optionInstance -> throw new IllegalArgumentException()}
    	controller.optionService = optionService.createMock()
    	
    	mockParams.id = options.find{it.priority == 3}.id
    	def model = controller.moveDown()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.not.movedDown.message', controller.flash.message
    }
    
    void testMoveUpOptimisticException() {
    	optionService.demand.moveUp() {Option optionInstance -> throw new OptimisticLockingFailureException("Exception")}
    	controller.optionService = optionService.createMock()
    	
    	mockParams.id = options.find{it.priority == 1}.id
    	def model = controller.moveUp()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.optimistic.locking.failure', controller.flash.message
    }
    
    void testMoveNonExiting() {
    	mockParams.id = 999
    	controller.moveUp()
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    	assertEquals 'Wrong science param in redirect', controller.nonExistingOptionRedirectionParams.science, redirectArgs.params.science
    	assertEquals 'Wrong type param in redirect', controller.nonExistingOptionRedirectionParams.type, redirectArgs.params.type
    }
    
    void testCreate() {
    	setScienceAndType(mockParams)
    	optionService.demand.getMaxPriority() { Science science, OptionType type -> 3}
    	controller.optionService = optionService.createMock()
    	
    	mockParams.name = 'Test'
    	def model = controller.create()
    	assertEquals 'Name was populated incorrectly', 'Test', model.optionInstance.name
    	assertEquals 'Wrong priority was set. Should be lowerst.', 4, model.optionInstance.priority
    	assertEquals 'Science was populated incorrectly', science, model.optionInstance.science
    	assertEquals 'Type was populated incorrectly', type, model.optionInstance.type
    }
    
    void testDelete() {
    	optionService.demand.delete() { Option optionInstance -> }
    	controller.optionService = optionService.createMock()
    	
    	mockParams.id = options.get(1).id
    	controller.delete()
    	assertEquals 'Wrong message was populated', 'default.deleted.message', controller.flash.message
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    }
    
    void testDeleteNonExiting() {
    	mockParams.id = 999
    	controller.delete()
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    	assertEquals 'Wrong science param in redirect', controller.nonExistingOptionRedirectionParams.science, redirectArgs.params.science
    	assertEquals 'Wrong type param in redirect', controller.nonExistingOptionRedirectionParams.type, redirectArgs.params.type
    }
    
    void testDeleteReferenced() {
    	optionService.demand.delete() { Option optionInstance -> throw new DataIntegrityViolationException("Error")}
    	controller.optionService = optionService.createMock()
    	
    	mockParams.id = options.get(1).id
    	controller.delete()
    	assertEquals 'Wrong message was populated', 'default.not.deleted.message', controller.flash.message
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    }
    
    void testSave() {
    	mockParams.name = "Some new test option"
    	mockParams.science = "PHYSICS"
    	mockParams.type = "RIGHT"
    	mockParams.priority = 5
    	controller.save()
    	
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong science param in redirect', Science.PHYSICS, redirectArgs.params.science
    	assertEquals 'Wrong type param in redirect', OptionType.RIGHT, redirectArgs.params.type
    	assertEquals 'Wrong message was populated', 'default.created.message', controller.flash.message
    	assertNotNull 'Option should have been saved into DB', Option.findByName(mockParams.name)
    }
    
    void testSaveValidatioError() {
    	setScienceAndType(mockParams)
    	mockParams.name = "Some new test option"
    	mockParams.priority = 0
    	def model = controller.save()
    	
    	assertEquals 'Create vew with error messages should have been returned', "create", renderArgs.view
    	assertNull 'Option should have not been saved into DB', Option.findByName(mockParams.name)
    }
    
    void testEdit() {
    	mockParams.id = options[0].id
    	def model = controller.edit()
    	assertEquals options[0], model.optionInstance
    }
    
    void testEditNonExisting() {
    	mockParams.id = 999
    	def model = controller.edit()
    	
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong science param in redirect', controller.nonExistingOptionRedirectionParams.science, redirectArgs.params.science
    	assertEquals 'Wrong type param in redirect', controller.nonExistingOptionRedirectionParams.type, redirectArgs.params.type
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testUpdate() {
    	mockParams.id = options[0].id
    	mockParams.name = "Option new name"
    	controller.update()
    	
    	assertEquals 'Option name was not updated', mockParams.name, Option.get(mockParams.id).name
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong science param in redirect', science, redirectArgs.params.science
    	assertEquals 'Wrong type param in redirect', type, redirectArgs.params.type
    	assertEquals 'Wrong message was populated', 'default.updated.message', controller.flash.message
		assertEquals 'Wrong updatedObjectId was populated', mockParams.id, controller.flash.updatedObjectId
    }
    
    void testUpdateValidatioError() {
    	mockParams.id = options[0].id
    	mockParams.name = ''
    	def model = controller.update()
    	
    	assertEquals 'Edit vew with error messages should have been returned', "edit", renderArgs.view
    	assertEquals 'Option should have not been saved into DB', options[0].name, Option.get(options[0].id).name
    }
    
    void testUpdateNonExisting() {
    	mockParams.id = 999
    	def model = controller.update()
    	
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong science param in redirect', controller.nonExistingOptionRedirectionParams.science, redirectArgs.params.science
    	assertEquals 'Wrong type param in redirect', controller.nonExistingOptionRedirectionParams.type, redirectArgs.params.type
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testUpdateOptimisticLockingError() {
    	options[0].version = 2
    	mockParams.id = options[0].id
    	mockParams.name = "Option new name"
    	mockParams.version = 1
    	controller.update()
    	
    	assertEquals 'Edit vew with error messages should have been returned', "edit", renderArgs.view
    	assertEquals 'Option should have not been saved into DB', options[0].name, Option.get(options[0].id).name
    	assertEquals 'Optimistic locking error should have been populated', "default.optimistic.locking.failure", renderArgs.model.optionInstance.errors.version
    }
}
