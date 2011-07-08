package org.ammrf.tf

import grails.test.*

class TechniqueServiceTests extends GrailsUnitTestCase {
    def service
    def leftOption
    def rightOption
    def combination
    def techniques = []
    
    protected void setUp() {
        super.setUp()
        
        leftOption = new Option(priority:1, name:"Test left option", science:Science.BIOLOGY, type:OptionType.LEFT)
        rightOption = new Option(priority:1, name:"Test right option", science:Science.BIOLOGY, type:OptionType.RIGHT)
        mockDomain(Option, [leftOption, rightOption])
        
        combination = new OptionCombination(left:leftOption, right:rightOption)
        mockDomain(OptionCombination, [combination])
        
        techniques.add(new Technique(name:'Technique 1', summary:'The summary', description:'The description'))
        techniques.add(new Technique(name:'Technique 2', summary:'The summary', description:'The description'))
        
        service = new TechniqueService()
    }
    
    protected void tearDown() {
        super.tearDown()
    }
    
    void callService() {
    	service.findTechniquesByOptionCombination(leftOption, rightOption, ['id', 'name', 'summary'])
    }
    
    void testFindTechniquesByOptionCombinationIncorrectLeftOptionType() {
    	leftOption.type = OptionType.RIGHT
    	shouldFail(IllegalArgumentException) {
    		callService()
    	}
    }
    
    void testFindTechniquesByOptionCombinationIncorrectRightOptionType() {
    	rightOption.type = OptionType.LEFT
    	shouldFail(IllegalArgumentException) {
    		callService()
    	}
    }
    
    void testFindTechniquesByOptionCombinationDifferentSciences() {
    	rightOption.science = Science.PHYSICS
    	shouldFail(IllegalArgumentException) {
    		callService()
    	}
    }
    
    void testUpdateTechniquesAssociationWithOptionCombinationIncorrectLeftOptionType() {
    	leftOption.type = OptionType.RIGHT
    	shouldFail(IllegalArgumentException) {
    		service.updateTechniquesAssociationWithOptionCombination([], leftOption, rightOption)
    	}
    }

}
