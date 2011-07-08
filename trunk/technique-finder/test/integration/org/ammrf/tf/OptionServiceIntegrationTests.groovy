package org.ammrf.tf

import grails.test.*

class OptionServiceIntegrationTests extends GrailsUnitTestCase {
	
	def sessionFactory
	def service
	def option1
	def option2
	def option3
	def option4
	def option5
    
	protected void setUp() {
        super.setUp()
        service = new OptionService()
        
        option1 = new Option(priority:1,
    			name: 'Test option',
    			science: Science.BIOLOGY, 
    			type: OptionType.RIGHT).save(flush:true)
        option2 = new Option(priority:3,
    			name: 'Test option',
    			science: Science.BIOLOGY, 
    			type: OptionType.RIGHT).save(flush:true)
        option3 = new Option(priority:2,
    			name: 'Test option',
    			science: Science.BIOLOGY, 
    			type: OptionType.RIGHT).save(flush:true)
    			
       option4 = new Option(priority:4,
    			name: 'Test option',
    			science: Science.BIOLOGY, 
    			type: OptionType.LEFT).save(flush:true)
       option5 = new Option(priority:5,
    			name: 'Test option',
    			science: Science.PHYSICS, 
    			type: OptionType.RIGHT).save(flush:true)
    }

    protected void tearDown() {
        super.tearDown()
		Option.executeUpdate("delete from Option")
    }

    void testGetMaxPriorityNoOptions() {
    	option4.delete(flush:true)
    	def maxPriority = service.getMaxPriority(Science.BIOLOGY, OptionType.LEFT)
    	assertEquals 0, maxPriority
    }
    
    void testGetMaxPriorityWithExistingOptions() {
    	def maxPriority = service.getMaxPriority(Science.BIOLOGY, OptionType.RIGHT)
    	assertEquals 3, maxPriority
    }
    
    void testDelete() {
      service.delete(Option.get(option3.id))
      sessionFactory.currentSession.clear() // clear hibernate session to fetch updates from DB
      
      assertNull 'Option should have been deleted', Option.get(option3.id)
      assertEquals 'Wrong total number of Options in DB after delete operation', 4, Option.count()
      assertEquals 'Wrong number of Options of the list we deleted from in DB after delete operation', 2, Option.countByScienceAndType(Science.BIOLOGY, OptionType.RIGHT)
      assertEquals 'Priority of the previous options should not be changed', 1, Option.get(option1.id).priority
      assertEquals 'Priority of the next options should be decreased', 2, Option.get(option2.id).priority
      
      assertEquals 'Priority of options in different list (type) should not be changed', 4, Option.get(option4.id).priority
      assertEquals 'Priority of options in different list (science) should not be changed', 5, Option.get(option5.id).priority
    }
    
    void testMoveUpMiddle() {
    	service.moveUp(option3)
    	sessionFactory.currentSession.flush()
    	sessionFactory.currentSession.clear() // clear hibernate session to fetch updates from DB
    	
    	assertEquals 'Priority was not decreased', 1, option3.priority
    	assertEquals 'Priority of the upper option was not increased', 2, Option.get(option1.id).priority
    	
    	assertEquals 'Priority of options in different list (type) should not be changed', 4, Option.get(option4.id).priority
        assertEquals 'Priority of options in different list (science) should not be changed', 5, Option.get(option5.id).priority
    }
    
    void testMoveUpTopmost() {
    	shouldFail(IllegalArgumentException) {
	    	service.moveUp(option1)
    	}
    }
    
    void testMoveDownMiddle() {
    	service.moveDown(option3)
    	sessionFactory.currentSession.flush()
    	sessionFactory.currentSession.clear() // clear hibernate session to fetch updates from DB
    	
    	assertEquals 'Priority was not increased', 3, option3.priority
    	assertEquals 'Priority of the lower option was not decreased', 2, Option.get(option2.id).priority
    	
    	assertEquals 'Priority of options in different list (type) should not be changed', 4, Option.get(option4.id).priority
        assertEquals 'Priority of options in different list (science) should not be changed', 5, Option.get(option5.id).priority
    }
    
    void testMoveDownLowerst() {
    	shouldFail(IllegalArgumentException) {
	    	service.moveDown(option2)
    	}
    }
}
