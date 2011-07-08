package org.ammrf.tf.admin

import org.ammrf.tf.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;


import grails.test.*

class LocationControllerTests extends ControllerUnitTestCase {
	def locations
	def locationService
	
    protected void setUp() {
        super.setUp()
        locations = []
        for(i in [2, 1, 3]) {
        	locations += new Location(priority:i,
        			institution: "Some test location $i", 
        			centerName: "Some test center name $i", 
        			address: "The address $i",
        			state: State.NSW, 
        			status: LocationStatus.ND)
        }
        mockDomain(Location, locations)
        locationService = mockFor(LocationService)
        LocationController.metaClass.message = { Map args -> return args['code']}
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListSorting() {
    	def model = controller.list()
    	assertEquals 'List sorting is incorrect', locations.sort{a, b -> a.priority.compareTo(b.priority)}, model.locationInstanceList
    }
    
    void testListMaxParamSmall() {
    	mockParams.max = 2
    	def model = controller.list()
    	assertEquals 'List size is incorrect', locations.size(), model.locationInstanceList.size()
    }
    
    void testListMaxParamBig() {
    	mockParams.max = 101
    	def model = controller.list()
    	assertEquals 'List size is incorrect', locations.size(), model.locationInstanceList.size()
    }
    
    void testMoveUp() {
    	locationService.demand.moveUp() {Location locationInstance -> }
    	controller.locationService = locationService.createMock()
    	
    	mockParams.id = locations.find{it.priority == 2}.id
    	def model = controller.moveUp()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.movedUp.message', controller.flash.message
    }
    
    void testMoveDown() {
    	locationService.demand.moveDown() {Location locationInstance -> }
    	controller.locationService = locationService.createMock()
    	
    	mockParams.id = locations.find{it.priority == 2}.id
    	def model = controller.moveDown()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.movedDown.message', controller.flash.message
    }
    
    void testMoveUpIllegal() {
    	locationService.demand.moveUp() {Location locationInstance -> throw new IllegalArgumentException()}
    	controller.locationService = locationService.createMock()
    	
    	mockParams.id = locations.find{it.priority == 1}.id
    	def model = controller.moveUp()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.not.movedUp.message', controller.flash.message
    }
    
    void testMoveDownIllegal() {
    	locationService.demand.moveDown() {Location locationInstance -> throw new IllegalArgumentException()}
    	controller.locationService = locationService.createMock()
    	
    	mockParams.id = locations.find{it.priority == 3}.id
    	def model = controller.moveDown()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.object.not.movedDown.message', controller.flash.message
    }
    
    void testMoveUpOptimisticException() {
    	locationService.demand.moveUp() {Location locationInstance -> throw new OptimisticLockingFailureException("Exception")}
    	controller.locationService = locationService.createMock()
    	
    	mockParams.id = locations.find{it.priority == 1}.id
    	def model = controller.moveUp()
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.optimistic.locking.failure', controller.flash.message
    }
    
    void testMoveNonExiting() {
    	mockParams.id = 999
    	controller.moveUp()
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testCreate() {
    	locationService.demand.getMaxPriority() { -> 3}
    	controller.locationService = locationService.createMock()
    	
    	mockParams.institution = 'Test'
    	def model = controller.create()
    	assertEquals 'Institution was populated incorrectly', 'Test', model.locationInstance.institution
    	assertEquals 'Wrong priority was set. Should be lowerst.', 4, model.locationInstance.priority
    }
    
    void testDelete() {
    	locationService.demand.delete() { Location locationInstance -> }
    	controller.locationService = locationService.createMock()
    	
    	mockParams.id = locations.get(1).id
    	controller.delete()
    	assertEquals 'Wrong message was populated', 'default.deleted.message', controller.flash.message
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    }
    
    void testDeleteNonExiting() {
    	mockParams.id = 999
    	controller.delete()
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testDeleteReferenced() {
    	locationService.demand.delete() { Location locationInstance -> throw new DataIntegrityViolationException("Error")}
    	controller.locationService = locationService.createMock()
    	
    	mockParams.id = locations.get(1).id
    	controller.delete()
    	assertEquals 'Wrong message was populated', 'default.not.deleted.message', controller.flash.message
    	assertEquals 'Wrong redirection', "list", redirectArgs.action
    }
}
