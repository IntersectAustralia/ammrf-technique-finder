package org.ammrf.tf.admin

import org.ammrf.tf.Location;
import org.ammrf.tf.Contact;
import org.ammrf.tf.LocationStatus;
import org.ammrf.tf.State;


import grails.test.*

class ContactControllerIntegrationTests extends ControllerUnitTestCase {
	def controller
        def contacts
	
    protected void setUp() {
        super.setUp()
        def location = new Location(priority: 1, institution: 'Some test location', centerName: 'Some test center name', address: 'The address', state: State.NSW, status:LocationStatus.ND)
        location.save(flush:true)
        contacts = []
        for(i in [2, 1, 3]) {
        	def contact = new Contact(title:'Test contact ' + i, name:'Some test name for a contact ' + i, position:'test', email:'test'+i+'@ammrf.org.au', techniqueContact:true, location:location, telephone:'phone '+i)
		contact.save(flush:true)
		contacts += contact
        }
	controller = new ContactController()
    }

    protected void tearDown() {
        super.tearDown()
		Contact.executeUpdate("delete from Contact")
		Location.executeUpdate("delete from Location")
    }

    void testListSorting() {
        def contactsSorted = contacts.sort { a, b ->
		int val = a.location?.priority - b?.location?.priority
                if (val == 0) {
                   return a.name?.compareTo(b?.name)
                } else {
                   return val
                }
        }
    	assertEquals 'List sorting is incorrect', contactsSorted, controller.list().contactInstanceList
    }
    
    void testParamAndOffset() {
    	controller.params.max = 2
        controller.params.offset = 2
        assertEquals contacts.size(), controller.list().contactInstanceList.size()
    }
    
}
