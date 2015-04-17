package org.ammrf.tf

import grails.test.*

class StaticContentTests extends GrailsUnitTestCase {
	
	def staticContent
	def bigText
	
    protected void setUp() {
        super.setUp()
        staticContent = new StaticContent(name: 'Some test name', text: 'Some test text');
        mockForConstraintsTests(StaticContent, [staticContent])
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    void testNameBlank() {
    	staticContent.name = ''
    	assertFalse 'Validation should have failed', staticContent.validate()
    	assertEquals 'blank', staticContent.errors.name
    }
    
    void testNameUnique() {
    	def staticContent2 = new StaticContent(name: staticContent.name, text: 'Some other test text')
    	assertFalse 'Validation should have failed', staticContent2.validate()
    	assertEquals 'unique', staticContent2.errors.name
    }
 
    void testTextMaxSize() {
    	def maxSize = 65537
    	StringBuilder bigText = new StringBuilder()
    	for(i in 1..maxSize) {
    		bigText.append('a')
    	}
    	assertEquals 'Text size is wrong', maxSize, bigText.length()
    	staticContent.text = bigText
    	assertFalse 'Validation should have failed', staticContent.validate()
    	assertEquals 'maxSize', staticContent.errors.text
    }
}
