package org.ammrf.tf

import grails.test.*

class LocationTests extends GrailsUnitTestCase {
	def location
	
    protected void setUp() {
        super.setUp()
        location = new Location(priority: 1, institution: 'Some test location', centerName: 'Some test center name', address: 'The address', state: State.NSW, status:LocationStatus.ND);
        mockForConstraintsTests(Location, [location])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testInstitutionBlank() {
    	Util.checkFieldBlank(this, location, 'institution')
    }
    
    void testInstitutionUnique() {
    	def location2 = new Location(institution: location.institution, centerName: 'Some test center name', address: 'The address', state: State.NSW, status:LocationStatus.ND);
    	assertFalse 'Validation should have failed', location2.validate()
    	assertEquals 'unique', location2.errors.institution
    }
    
    void testPriorityOneBased() {
    	location.priority = 0
    	assertFalse 'Validation should have failed', location.validate()
    	assertEquals 'min', location.errors.priority
    }
    
    void testCenterNameMaxSize() {
    	Util.checkFieldLength(this, location, 'centerName', 255)
    }
        
    void testInstitutionMaxSize() {
    	Util.checkFieldLength(this, location, 'institution', 255)
    }
    
}
