package org.ammrf.tf

import grails.test.*

class StaticContentServiceTests extends GrailsUnitTestCase {
    
	def staticContent
	def service;
	
	protected void setUp() {
        super.setUp()
        staticContent = new StaticContent(name: 'Some test name', text: 'Some test text');
        mockDomain(StaticContent, [staticContent])
        service = new StaticContentService()
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    void testFindText() {
    	assertEquals 'Correct text was not found', staticContent.text, service.findText(staticContent.name)
    }
    
    void testFindTextNotFound() {
    	assertEquals 'Nothing should be found', null, service.findText('Some non-existing name')
    }
}
