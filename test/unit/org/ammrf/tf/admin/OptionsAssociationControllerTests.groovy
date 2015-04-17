
package org.ammrf.tf.admin

import java.util.List;
import org.ammrf.tf.*;
import grails.test.*

class OptionsAssociationControllerTests extends ControllerUnitTestCase {
	def techniqueService
	def techniques = []
	def leftOption
	def rightOption
	
    protected void setUp() {
        super.setUp()
        techniqueService = mockFor(TechniqueService)
        for(i in 1..3) {
        	techniques += [id:i, name:"Technique $i"]
        }
        
        leftOption = new Option(priority:1, name: "left bio option", science: Science.BIOLOGY, type:OptionType.LEFT)
    	rightOption = new Option(priority:1, name: "right bio option", science: Science.BIOLOGY, type:OptionType.RIGHT)
        mockDomain(Option, [leftOption, rightOption])
        OptionsAssociationController.metaClass.message = { Map args -> return args['code']}
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListAssociatedTechniques() {
    	techniqueService.demand.findTechniquesByOptionCombination(){ Option left, Option right, List<String> fields ->
    		if(left.equals(leftOption) && right.equals(rightOption) && fields.equals(['id', 'name'])) {
    			return techniques
    		} else {
    			return []
    		}
    	}
    	controller.techniqueService = techniqueService.createMock()
    	mockParams.leftOption = leftOption.id
    	mockParams.rightOption = rightOption.id
    	
    	def model = controller.listAssociatedTechniques()
    	assertEquals 'Wrong technique list', techniques, model.techniques
    	assertEquals 'Template is incorrect', 'associatedTechniques', renderArgs.template
    }
    
    void testListAssociatedTechniquesNonExistingLeftOption() {
    	mockParams.leftOption = 999
    	mockParams.rightOption = rightOption.id
    	
    	controller.listAssociatedTechniques()
    	assertEquals 'Template is incorrect', '/templates/ajaxError', renderArgs.template
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testListAssociatedTechniquesNonExistingRightOption() {
    	mockParams.leftOption = leftOption.id
    	mockParams.rightOption = 999
    	
    	controller.listAssociatedTechniques()
    	assertEquals 'Template is incorrect', '/templates/ajaxError', renderArgs.template
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testListAssociatedTechniquesIncorrectOptions() {
    	techniqueService.demand.findTechniquesByOptionCombination(){ Option left, Option right, List<String> fields ->
			throw new IllegalArgumentException()
		}
    	
		controller.techniqueService = techniqueService.createMock()
		mockParams.leftOption = rightOption.id
		mockParams.rightOption = leftOption.id
		
    	controller.listAssociatedTechniques()
    	assertEquals 'Template is incorrect', '/templates/ajaxError', renderArgs.template
    	assertEquals 'Wrong message was populated', 'tf.ajax.error', controller.flash.message
    }
    
    void testListTechniques() {
    	techniqueService.demand.getTechniquesNotInList(){ Long[] techniqueIds ->
			return techniques
		}
		
		controller.techniqueService = techniqueService.createMock()
		
		def model = controller.listTechniques(new TechniqueIdsCommand())
		assertEquals 'Wrong technique list', techniques, model.techniques
    	assertEquals 'Template is incorrect', 'techniqueList', renderArgs.template
    }
    
    void testUpdate() {
    	techniqueService.demand.updateTechniquesAssociationWithOptionCombination(){ Long[] techniqueIds, Option left, Option right ->
		}
		
		controller.techniqueService = techniqueService.createMock()
		mockParams.leftOption = leftOption.id
    	mockParams.rightOption = rightOption.id
    	
		def model = controller.update(new TechniqueIdsCommand())
    	assertEquals 'Wrong message was populated', 'tf.option.assiciations.updated', controller.flash.message
    	assertEquals 'Redirect is incorrect', 'listAssociatedTechniques', redirectArgs.action
    	assertEquals 'leftOption param is wrong', leftOption.id, redirectArgs.params.leftOption
    	assertEquals 'rightOption param is wrong', rightOption.id, redirectArgs.params.rightOption
    }
    
    void testUpdateNonExistingLeftOption() {
    	mockParams.leftOption = 999
    	mockParams.rightOption = rightOption.id
    	
    	controller.update(new TechniqueIdsCommand())
    	assertEquals 'Template is incorrect', '/templates/ajaxError', renderArgs.template
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testUpdateNonExistingRightOption() {
    	mockParams.leftOption = leftOption.id
    	mockParams.rightOption = 999
    	
    	controller.update(new TechniqueIdsCommand())
    	assertEquals 'Template is incorrect', '/templates/ajaxError', renderArgs.template
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testUpdateIncorrectOptions() {
    	techniqueService.demand.updateTechniquesAssociationWithOptionCombination(){ Long[] techniqueIds, Option left, Option right ->
			throw new IllegalArgumentException()
		}
    	
		controller.techniqueService = techniqueService.createMock()
		mockParams.leftOption = rightOption.id
		mockParams.rightOption = leftOption.id
		
		controller.update(new TechniqueIdsCommand())
    	assertEquals 'Template is incorrect', '/templates/ajaxError', renderArgs.template
    	assertEquals 'Wrong message was populated', 'tf.ajax.error', controller.flash.message
    }
}
