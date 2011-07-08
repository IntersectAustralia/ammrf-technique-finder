package org.ammrf.tf

import grails.test.*

class CaseStudyTests extends GrailsUnitTestCase {
	def caseStudy
	
    protected void setUp() {
        super.setUp()
        caseStudy = new CaseStudy(name:'Test case study', url:'http://www.ammrf.org.au')
        mockForConstraintsTests(CaseStudy, [caseStudy])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNameBlank() {
    	caseStudy.name = ''
    	assertFalse 'Validation should have failed', caseStudy.validate()
    	assertEquals 'blank', caseStudy.errors.name
    }
    
    void testToString() {
    	caseStudy.name = 'anything'
    	assertEquals caseStudy.name, caseStudy.toString()
    }
    
    void testUrlInvalid() {
    	caseStudy.url = 'blah'
    	assertFalse 'Validation should have failed', caseStudy.validate()
    	assertEquals 'url', caseStudy.errors.url
    }
    
    void testNameLenght() {
    	Util.checkFieldLength this, caseStudy, 'name', 255
    }
    
    void testUrlLength() {
    	Util.checkUrlLength this, caseStudy, 'url', 1024
    }
    
    void testUrlBlank() {
		Util.checkFieldBlank this, caseStudy, 'url'
	}
	
	void testComparator() {
		def caseStudy2 = new CaseStudy(name:"$caseStudy.name 2", url:caseStudy.url)
		assertTrue 'Should use caseStudy.name for ordering', caseStudy.compareTo(caseStudy2) < 0
  }
}
