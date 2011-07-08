package org.ammrf.tf

import grails.test.*

class OptionCombinationTests extends GrailsUnitTestCase {
	def left
	def right
	def combination
	
    protected void setUp() {
        super.setUp()
        left = new Option(name:'Test bio option left', science:Science.BIOLOGY, type:OptionType.LEFT, priority:1)
        right = new Option(name:'Test bio option right', science:Science.BIOLOGY, type:OptionType.RIGHT, priority:1)
        combination = new OptionCombination(left:left, right:right, priority:1)
        mockForConstraintsTests(OptionCombination, [combination])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testPriorityOneBased() {
    	combination.priority = 0
    	assertFalse 'Validation should have failed', combination.validate()
    	assertEquals 'min', combination.errors.priority
    }
    
    void testScienceNotEqual() {
    	right.science = Science.PHYSICS
    	assertFalse 'Validation should have failed', combination.validate()
    	assertEquals 'validator', combination.errors.left
    }
    
    void testLeftTypeIncorrect() {
    	left.type = OptionType.RIGHT
    	assertFalse 'Validation should have failed', combination.validate()
    	assertEquals 'validator', combination.errors.left
    }
    
    void testRightTypeIncorrect() {
    	right.type = OptionType.LEFT
    	assertFalse 'Validation should have failed', combination.validate()
    	assertEquals 'validator', combination.errors.left
    }
}
