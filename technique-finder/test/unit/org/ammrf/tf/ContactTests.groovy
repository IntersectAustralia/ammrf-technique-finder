package org.ammrf.tf

import grails.test.*

class ContactTests extends GrailsUnitTestCase {

    def contact
    def location2
    def location
	
    protected void setUp() {
        super.setUp()
        location = new Location(priority: 1, institution: 'Some test location', centerName: 'Some test center name', address: 'The address', state: State.NSW, status:LocationStatus.LL)
        location2 = new Location(priority: 2, institution: 'Another test location', centerName: 'Center name 2', address: 'The address 2', state: State.ACT, status:LocationStatus.ND)
        contact = new Contact(title:'Test contact', name:'Some test name for a contact', position:'test', email:'test@ammrf.org.au', techniqueContact:true, location:location, telephone:'')
        mockForConstraintsTests(Contact, [location, contact])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testTitleMaxSize() {
    	Util.checkFieldLength this, contact, 'title', 255
    }
    
    void testNameBlank() {
    	Util.checkFieldBlank this, contact, 'name'
    }
    
    void testEmailAndTelephoneBlank() {
    	contact.email = ''
        contact.telephone = ''
    	assertFalse 'Validation should have failed', contact.validate()
    	assertEquals 'validator', contact.errors.email
    	assertEquals 'validator', contact.errors.telephone
    }
    
    void testEmailOrTelephoneBlank() {
    	contact.email = 'c@test.com'
        contact.telephone = ''
    	assertTrue 'Validation should have passed', contact.validate()
    	contact.email = ''
        contact.telephone = '02 3333 4444'
    	assertTrue 'Validation should have passed', contact.validate()
    }
    
    void testEmailInvalid() {
    	contact.email = 'test'
    	assertFalse 'Validation should have failed', contact.validate()
    	assertEquals 'email', contact.errors.email
    }
    
    void testEmailValid() {
    	contact.email = 'test@ammrf.org.au'
    	assertTrue 'Validation should have passed', contact.validate()
    }
    
    void testNameLenght() {
    	Util.checkFieldLength this, contact, 'name', 255
    }
    
    void testEmailLenght() {
    	def domainPart = "@lala.com"
    	def maxSize = 256 - domainPart.length()
    	StringBuilder bigText = new StringBuilder()
    	for(i in 1..maxSize) {
    		bigText.append('a')
    	}
    	bigText.append(domainPart)
    	contact.email = bigText
    	assertFalse 'Validation should have failed', contact.validate()
    	assertEquals 'maxSize', contact.errors.email
    }
    
    void testPositionLenght() {
    	Util.checkFieldLength this, contact, 'position', 255
    }

    void testTelephoneLenght() {
    	Util.checkFieldLength this, contact, 'telephone', 25
    }
    

    void testComparatorDifferentLocation() {
         def contact2 = new Contact(title:'Test contact', name:contact.name, position:'test', email:'test2@ammrf.org.au', techniqueContact:true, location:location2, telephone:'')
         assertTrue 'Should use location.priority for ordering', contact.compareTo(contact2) < 0
   }

    void testComparatorSameLocation() {
         def contact2 = new Contact(title:'X', name:contact.name, position:'test', email:'test2@ammrf.org.au', techniqueContact:true, location:contact.location, telephone:'')
         assertTrue 'Should use name for ordering in same institution and they are equal', contact.compareTo(contact2) == 0
         contact2 = new Contact(title:'X', name:'A', position:'test', email:'test2@ammrf.org.au', techniqueContact:true, location:contact.location, telephone:'')
         assertTrue 'Should use name for ordering in same institution', contact.compareTo(contact2) > 0
    }
}
