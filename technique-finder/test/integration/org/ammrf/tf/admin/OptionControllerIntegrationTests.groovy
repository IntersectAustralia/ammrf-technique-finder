package org.ammrf.tf.admin

import org.ammrf.tf.*;
import org.springframework.dao.DataIntegrityViolationException;
import grails.test.*

class OptionControllerIntegrationTests extends GroovyTestCase {
	
	def controller
	def options
	
    protected void setUp() {
        super.setUp()
        controller = new OptionController()
        options = []
        for(i in [2, 1, 3]) {
        	options += new Option(priority:i,
       	 		name: "Some test option $i", 
       	 		science: Science.BIOLOGY, 
       	 		type: OptionType.LEFT).save(flush:true)
        }
        
        def option4 = new Option(priority:4,
    			name: 'Test option',
    			science: Science.BIOLOGY, 
    			type: OptionType.RIGHT).save(flush:true)
        def option5 = new Option(priority:5,
    			name: 'Test option',
    			science: Science.PHYSICS, 
    			type: OptionType.RIGHT).save(flush:true)
    	def option6 = new Option(priority:1,
    			name: 'Test option',
    			science: Science.PHYSICS, 
    			type: OptionType.LEFT).save(flush:true)
    }

    protected void tearDown() {
        super.tearDown()
		Option.executeUpdate("delete from Option")
    }
    
    private setScienceAndType(params) {
    	params.science = "BIOLOGY"
    	params.type = "LEFT"
    }

    void testListSorting() {
    	setScienceAndType(controller.params)
    	def model = controller.list()
    	assertEquals 'List sorting is incorrect', options.sort{a, b -> a.priority.compareTo(b.priority)}, model.optionInstanceList
		assertNull 'updatedObjectId should be null', controller.flash.updatedObjectId
    }
    
    void testListMaxParamSmall() {
    	setScienceAndType(controller.params)
    	controller.params.max = 2
    	def model = controller.list()
    	assertEquals 'List size is incorrect', options.size(), model.optionInstanceList.size()
    }
    
    void testListMaxParamBig() {
    	setScienceAndType(controller.params)
    	controller.params.max = 101
    	def model = controller.list()
    	assertEquals 'List size is incorrect', options.size(), model.optionInstanceList.size()
    }
    
}
