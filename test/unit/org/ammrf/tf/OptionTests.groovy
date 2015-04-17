package org.ammrf.tf

import grails.test.*

class OptionTests extends GrailsUnitTestCase {
	def option
	
    protected void setUp() {
        super.setUp()
        option = new Option(name:'Test option', science:Science.BIOLOGY, type:OptionType.RIGHT, priority:1)
        mockForConstraintsTests(Option, [option])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNameBlank() {
    	option.name = ''
    	assertFalse 'Validation should have failed', option.validate()
    	assertEquals 'blank', option.errors.name
    }
    
    void testNameLength() {
    	Util.checkFieldLength this, option, 'name', 255
    }
    
    
    void testPriorityOneBased() {
    	option.priority = 0
    	assertFalse 'Validation should have failed', option.validate()
    	assertEquals 'min', option.errors.priority
    }
    
    void testToString() {
    	assertEquals option.name, option.toString()
    }
}
