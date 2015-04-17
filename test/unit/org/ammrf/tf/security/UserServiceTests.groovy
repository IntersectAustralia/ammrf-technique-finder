package org.ammrf.tf.security

import grails.test.*
import org.hibernate.Session;
import org.hibernate.SessionFactory;

class UserServiceTests extends GrailsUnitTestCase {
	def service
	def roleSuper
	def roleAdmin
	def user
	
    protected void setUp() {
        super.setUp()
        user = new User(id:5, username:"test@ammrf.org.au", fullName:"Test user", isSuper:false, 
        		passwd:"test", password:"unused", passwordConfirmation:"unused", secondEmail:'', secondEmailConfirmation:'')
        mockDomain(User, [user])
        roleSuper = new Role(authority:"ROLE_SUPER", description:"Super admin", people:[])
        roleAdmin = new Role(authority:"ROLE_ADMIN", description:"Admin")
        mockDomain(Role, [roleSuper, roleAdmin])
        roleAdmin.addToPeople(user)
        
        service = new UserService()
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    private void recordSessionFlush() {
    	Session sessionMock = [flush:{}] as org.hibernate.classic.Session
    	SessionFactory sessionFactoryMock = [getCurrentSession:{return sessionMock}] as SessionFactory
    	service.sessionFactory = sessionFactoryMock
    }
    
    void testCreateUserAdmin() {
    	recordSessionFlush()
    	def success = service.createUser(user)
    	assertTrue "Service method should have returned true", success
    	assertTrue "User should have been added to ROLE_ADMIN role", roleAdmin.people.contains(user)
    	assertFalse "User should not have been added to ROLE_SUPER role", roleSuper.people.contains(user)
    }
    
    void testCreateUserSuper() {
    	recordSessionFlush()
    	user.isSuper = true
    	def success = service.createUser(user)
    	assertTrue "Service method should have returned true", success
    	assertTrue "User should have been added to ROLE_ADMIN role", roleAdmin.people.contains(user)
    	assertTrue "User should have been added to ROLE_SUPER role", roleSuper.people.contains(user)
    }
    
    void testDeleteUserAdmin() {
    	service.deleteUser(user)
    	assertNull "User should have been deleted", User.get(user.id)
    	assertFalse "User should have been deleted from ROLE_ADMIN", roleAdmin.people.contains(user)
    }
    
    void testDeleteUserSuper() {
    	user.isSuper = true
    	roleSuper.addToPeople(user)
    	
    	service.deleteUser(user)
    	assertNull "User should have been deleted", User.get(user.id)
    	assertFalse "User should have been deleted from ROLE_ADMIN", roleAdmin.people.contains(user)
    	assertFalse "User should have been deleted from ROLE_SUPER", roleSuper.people.contains(user)
    }
    
    void testUpdateUserAndRolesAddSuper() {
    	recordSessionFlush()
    	user.isSuper = true
    	def success = service.updateUserAndRoles(user)
    	assertTrue "Service method should have returned true", success
    	assertTrue "User should have been added to ROLE_SUPER role", roleSuper.people.contains(user)
    }
    
    void testUpdateUserAndRolesRemoveSuper() {
    	recordSessionFlush()
    	user.isSuper = true
    	roleSuper.addToPeople(user).save(flush:true)
    	assertTrue "Precondition: User should have ROLE_SUPER", roleSuper.people.contains(user)
    	user.isSuper = false
    	
    	def success = service.updateUserAndRoles(user)
    	assertTrue "Service method should have returned true", success
    	assertFalse "User should have been deleted from ROLE_SUPER", roleSuper.people.contains(user)
    }
    
    void testUpdateUserAndRolesRootAccountRemoveSuper() {
    	recordSessionFlush()
    	user.isSuper = true
    	user.id = 1
    	roleSuper.addToPeople(user).save(flush:true)
    	assertTrue "Precondition: User should have ROLE_SUPER", roleSuper.people.contains(user)
    	user.isSuper = false
    	
    	def success = service.updateUserAndRoles(user)
    	assertTrue "Service method should have returned true", success
    	assertTrue "Root User should have kept ROLE_SUPER", roleSuper.people.contains(user)
    }
}
