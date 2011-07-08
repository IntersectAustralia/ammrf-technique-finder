package org.ammrf.tf

import grails.test.*

class TechniqueTests extends GrailsUnitTestCase {
	def technique
	
    protected void setUp() {
        super.setUp()
        technique = new Technique(name: 'Some test name', summary: 'Some test text', description: 'The description');
        mockForConstraintsTests(Technique, [technique])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNameBlank() {
    	technique.name = ''
    	assertFalse 'Validation should have failed', technique.validate()
    	assertEquals 'blank', technique.errors.name
    }
    
    void testSummaryBlank() {
    	technique.summary = ''
    	assertFalse 'Validation should have failed', technique.validate()
    	assertEquals 'blank', technique.errors.summary
    }
    
    void testDescriptionBlank() {
    	technique.description = ''
    	assertFalse 'Validation should have failed', technique.validate()
    	assertEquals 'blank', technique.errors.description
    }
    
    void testNameUnique() {
    	def technique2 = new Technique(name: technique.name, summary: 'Some test text', description: 'The description')
    	assertFalse 'Validation should have failed', technique2.validate()
    	assertEquals 'unique', technique2.errors.name
    }
 
    void testDescriptionMaxSize() {
    	def maxSize = 65537
    	StringBuilder bigText = new StringBuilder()
    	for(i in 1..maxSize) {
    		bigText.append('a')
    	}
    	assertEquals 'Description size is wrong', maxSize, bigText.length()
    	technique.description = bigText
    	assertFalse 'Validation should have failed', technique.validate()
    	assertEquals 'maxSize', technique.errors.description
    }
    
    void testSummaryMaxSize() {
    	def maxSize = 1025
    	StringBuilder bigText = new StringBuilder()
    	for(i in 1..maxSize) {
    		bigText.append('a')
    	}
    	assertEquals 'Summary size is wrong', maxSize, bigText.length()
    	technique.summary = bigText
    	assertFalse 'Validation should have failed', technique.validate()
    	assertEquals 'maxSize', technique.errors.summary
    }
    
    void testToString() {
    	assertEquals technique.name, technique.toString()
    }
}
