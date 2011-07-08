package org.ammrf.tf

import grails.test.*

class LocationServiceIntegrationTests extends GrailsUnitTestCase {
	
	def sessionFactory
	def service
	
    protected void setUp() {
        super.setUp()
        service = new LocationService()
    }

    protected void tearDown() {
        super.tearDown()
		Location.executeUpdate("delete Location")
    }

    void testGetMaxPriorityNoLocations() {
    	def maxPriority = service.getMaxPriority()
    	assertEquals 0, maxPriority
    }
    
    void testGetMaxPriorityWithExistingLocations() {
    	new Location(priority:1,
    			institution: 'Some test location', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save()
    	new Location(priority:2,
    			institution: 'Some test location 2', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save()
    	def maxPriority = service.getMaxPriority()
    	assertEquals 2, maxPriority
    }
    
    void testDelete() {
    	def location1 = new Location(priority:1,
    			institution: 'Some test location', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save(flush:true)
    	def location2 = new Location(priority:3,
    			institution: 'Some test location 2', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save(flush:true)
       def location3 = new Location(priority:2,
    			institution: 'Some test location 3', 
    			centerName: 'Some test center name', 
    			address: 'The address', state: 
    			State.NSW, 
    			status:LocationStatus.ND).save(flush:true)
      
      service.delete(Location.get(location3.id))
      sessionFactory.currentSession.clear() // clear hibernate session to fetch updates from DB
      
      assertNull 'Location should have been deleted', Location.get(location3.id)
      assertEquals 'Wrong number of Locations in DB after delete operation', 2, Location.count()
      assertEquals 'Priority of the previous locations should not be changed', 1, Location.get(location1.id).priority
      assertEquals 'Priority of the next locations should be decreased', 2, Location.get(location2.id).priority
    }
}
