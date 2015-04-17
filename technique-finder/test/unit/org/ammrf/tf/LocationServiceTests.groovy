package org.ammrf.tf

import grails.test.GrailsUnitTestCase;

class LocationServiceTests extends GrailsUnitTestCase {
	def service
	def location1
	def location2
	def location3
	
	protected void setUp() {
        super.setUp()
        service = new LocationService()
        mockDomain(Location)
        location1 = new Location(priority:1,
    			institution: 'Some test location', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save(flus:true)
    	location2 = new Location(priority:3,
    			institution: 'Some test location 2', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save(flus:true)
        location3 = new Location(priority:2,
    			institution: 'Some test location 3', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save(flus:true)
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    void testMoveUpMiddle() {
    	service.moveUp(location3)
    	assertEquals 'Priority was not decreased', 1, location3.priority
    	assertEquals 'Priority of the upper location was not increased', 2, Location.get(location1.id).priority
    }
    
    void testMoveUpTopmost() {
    	shouldFail(IllegalArgumentException) {
	    	service.moveUp(location1)
    	}
    }
    
    void testMoveDownMiddle() {
    	service.moveDown(location3)
    	assertEquals 'Priority was not increased', 3, location3.priority
    	assertEquals 'Priority of the lower location was not decreased', 2, Location.get(location2.id).priority
    }
    
    void testMoveDownLowerst() {
    	shouldFail(IllegalArgumentException) {
	    	service.moveDown(location2)
    	}
    }
}
