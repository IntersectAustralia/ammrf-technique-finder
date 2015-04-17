package org.ammrf.tf.security

import org.grails.plugins.springsecurity.service.AuthenticateService;
import grails.test.*

class UserControllerTests extends ControllerUnitTestCase {
	def authenticateService
    def userService
    def roleSuper
    def roleAdmin
    def userSuper
    def userAdmin
    
    protected void setUp() {
        super.setUp()
        UserController.metaClass.message = { Map args -> return args['code']}
        
        userSuper = new User(username:"admin@ammrf.org.au", fullName:"Super test user", isSuper:true, 
        		passwd:"test", password:"unused", passwordConfirmation:"unused", secondEmail:'', secondEmailConfirmation:'')
        userAdmin = new User(username:"test@ammrf.org.au", fullName:"Admin test user", isSuper:false, 
        		passwd:"test", password:"unused", passwordConfirmation:"unused", secondEmail:'', secondEmailConfirmation:'')
        mockDomain(User, [userSuper, userAdmin])
        roleSuper = new Role(authority:"ROLE_SUPER", description:"Super admin", people:[])
        roleAdmin = new Role(authority:"ROLE_ADMIN", description:"Admin")
        mockDomain(Role, [roleSuper, roleAdmin])
        roleAdmin.addToPeople(userAdmin)
        roleAdmin.addToPeople(userSuper)
        roleSuper.addToPeople(userSuper)
        
        authenticateService = mockFor(AuthenticateService)
        userService = mockFor(UserService)
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    private void setUpUser(boolean isSuper) {
    	if(isSuper) {
    		authenticateService.demand.ifAllGranted(){String pwd -> return true}
        	controller.authenticateService = authenticateService.createMock()
    	} else {
    		authenticateService.demand.ifAllGranted(){String pwd -> return false}
        	authenticateService.demand.userDomain(){-> return userAdmin}
        	controller.authenticateService = authenticateService.createMock()
    	}
    }
    
    void testListDefaultSorting() {
    	def model = controller.list()
    	assertEquals 'Wrong user list', [userSuper, userAdmin], model.userInstanceList
    }
    
    void testListFullNameSorting() {
    	mockParams.sort = 'fullName'
    	def model = controller.list()
    	assertEquals 'Wrong user list', [userAdmin, userSuper], model.userInstanceList
    }
    
    void testListMaxSize() {
    	mockParams.max = '1'
    	def model = controller.list()
    	assertEquals 'Wrong user list', [userSuper, userAdmin], model.userInstanceList
    }
    
    void testCreate() {
    	def model = controller.create()
    	assertNull 'No Id should have been set in the new model object', model.userInstance.id
    }
    
    void testSave() {
    	def passwd = "testpasswd"
    	authenticateService.demand.encodePassword(){String pwd -> 
    		if("admin".equals(pwd)) {
    			return passwd
    		} else {
    			return null
    		}
    	}
    	controller.authenticateService = authenticateService.createMock()
    	userService.demand.createUser(){User user -> return true}
    	controller.userService = userService.createMock()
    	mockParams.id = userAdmin.id
    	
    	controller.save()
    	assertEquals 'Wrong redirect', 'show', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.created.message', controller.flash.message
    	assertEquals 'Wrong  id was passed to the next request', userAdmin.id, redirectArgs.id
    	assertEquals 'superAdmin parameter should have been passed to the next request', true, redirectArgs.params.superAdmin
    }
    
    void testSaveFailed() {
    	def passwd = "testpasswd"
    	authenticateService.demand.encodePassword(){String pwd -> return passwd}
    	controller.authenticateService = authenticateService.createMock()
    	userService.demand.createUser(){User user -> return false}
    	controller.userService = userService.createMock()
    	mockParams.username = userAdmin.username
    	
    	controller.save()
    	assertEquals 'Wrong view', 'create', renderArgs.view
    	assertEquals 'Wrong  username was passed to the view', userAdmin.username, renderArgs.model.userInstance.username
    }
    
    void testShowBySuperAdmin() {
    	setUpUser(true)
    	mockParams.id = userAdmin.id
    	mockParams.superAdmin = true
    	
    	def model = controller.show()
    	assertEquals 'Wrong user in the model', userAdmin, model.userInstance
    	assertEquals 'Wrong superAdmin in the model', mockParams.superAdmin, model.superAdmin
    }
    
    void testShowOwnAccountNoSuper() {
    	setUpUser(false)
    	mockParams.id = userAdmin.id
    	mockParams.superAdmin = false
    	
    	def model = controller.show()
    	assertEquals 'Wrong user in the model', userAdmin, model.userInstance
    	assertEquals 'Wrong superAdmin in the model', mockParams.superAdmin, model.superAdmin
    }
    
    void testShowNonExistingUser() {
    	setUpUser(true)
    	mockParams.id = 999
    	
    	controller.show()
    	assertEquals 'Wrong redirection', 'list', redirectArgs.action
    }
    
    void testAcessDenied() {
    	setUpUser(false)
    	mockParams.id = userSuper.id
    	
    	controller.show()
    	assertEquals 'Wrong redirection controller', 'login', redirectArgs.controller
    	assertEquals 'Wrong redirection action', 'denied', redirectArgs.action
    }
    
    void testEditOwnAccount() {
    	setUpUser(false)
    	mockParams.id = userAdmin.id
    	
    	def model = controller.edit()
    	assertEquals 'Wrong user in the model', userAdmin, model.userInstance
    }
    
    void testEditAsSuper() {
    	setUpUser(true)
    	mockParams.id = userAdmin.id
    	
    	def model = controller.editAsSuper()
    	assertEquals 'Wrong user in the model', userAdmin, model.userInstance
    }
    
    void testUpdateAsSuper() {
    	setUpUser(true)
    	userService.demand.updateUserAndRoles(){User user -> return true}
    	controller.userService = userService.createMock()
    	mockParams.id = userAdmin.id
    	
    	controller.updateAsSuper()
    	assertEquals 'Wrong redirect', 'show', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.updated.message', controller.flash.message
    	assertEquals 'Wrong  id was passed to the next request', userAdmin.id, redirectArgs.id
    	assertEquals 'superAdmin parameter should have been passed to the next request', true, redirectArgs.params.superAdmin
    }
    
    void testUpdateAsSuperValidationErros() {
    	setUpUser(true)
    	userService.demand.updateUserAndRoles(){User user -> return false}
    	controller.userService = userService.createMock()
    	mockParams.id = userAdmin.id
    	
    	controller.updateAsSuper()
    	assertEquals 'Wrong view', 'editAsSuper', renderArgs.view
    	assertEquals 'Wrong username in the model', userAdmin.username, renderArgs.model.userInstance.username
    }
    
    void testUpdateAsSuperOptimisticLockingErros() {
    	setUpUser(true)
    	userAdmin.version = 2
    	userAdmin.save(flush:true)
    	mockParams.id = userAdmin.id
    	mockParams.version = 1
    	
    	controller.updateAsSuper()
    	assertEquals 'Wrong view', 'editAsSuper', renderArgs.view
    	assertEquals 'Wrong message was populated', 'default.optimistic.locking.failure', userAdmin.errors.version
    	assertEquals 'Wrong username in the model', userAdmin.username, renderArgs.model.userInstance.username
    }
    
    private void checkUpdateOwnAccount(fieldName, newValue) {
    	setUpUser(false)
    	mockParams.id = userAdmin.id
    	mockParams."${fieldName}" = newValue
    	
    	controller.update()
    	assertEquals 'Wrong redirect', 'show', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.updated.message', controller.flash.message
    	assertEquals 'Wrong  id was passed to the next request', userAdmin.id, redirectArgs.id
    	assertEquals "Wrong new value for the field $fieldName was set", newValue, userAdmin."${fieldName}" 
    	assertNull 'superAdmin parameter should be null', redirectArgs.params?.superAdmin
    }
    
    void testUpateOwnAccountFullName() {
    	mockParams.passsword = ''
    	mockParams.secondEmail = ''
    	mockParams.secondEmailConfirmation = ''
    	checkUpdateOwnAccount('fullName', 'new full name')
    }
    
    void testUpateOwnAccountPassword() {
    	def newPasswordValue = 'newpwd'
    	mockParams.fullName = userAdmin.fullName
    	mockParams.passwordConfirmation = newPasswordValue
    	mockParams.secondEmail = ''
    	mockParams.secondEmailConfirmation = ''
    	
    	authenticateService.demand.ifAllGranted(){String pwd -> return false}
    	authenticateService.demand.userDomain(){-> return userAdmin}
    	authenticateService.demand.encodePassword(){String pwd -> return newPasswordValue}
    	controller.authenticateService = authenticateService.createMock()
    	
    	checkUpdateOwnAccount('password', newPasswordValue)
    }
    
    void testUpateOwnAccountSecondEmail() {
    	mockParams.fullName = "new user full name"
    	mockParams.passsword = ''
    	mockParams.secondEmailConfirmation = 'testnew@test.com'
    	checkUpdateOwnAccount('secondEmail', 'testnew@test.com')
    }
    
    void testUpateOwnAccountValidationErrors() {
    	setUpUser(false)
    	mockParams.id = userAdmin.id
    	mockParams.fullName = ''
    	mockParams.secondEmail = ''
    	mockParams.secondEmailConfirmation = ''
    	
    	controller.update()
    	assertEquals 'Wrong view', 'edit', renderArgs.view
    	assertEquals 'Wrong username in the model', userAdmin.username, renderArgs.model.userInstance.username
    }
    
    void testUpdateOptimisticLockingErros() {
    	setUpUser(false)
    	userAdmin.version = 2
    	userAdmin.save(flush:true)
    	mockParams.id = userAdmin.id
    	mockParams.version = 1
    	
    	controller.update()
    	assertEquals 'Wrong view', 'edit', renderArgs.view
    	assertEquals 'Wrong message was populated', 'default.optimistic.locking.failure', userAdmin.errors.version
    	assertEquals 'Wrong username in the model', userAdmin.username, renderArgs.model.userInstance.username
    }
    
    private void setUpUserForDelete() {
    	authenticateService.demand.userDomain(){-> return userSuper}
    	controller.authenticateService = authenticateService.createMock()
    }
    
    void testDelete() {
    	setUpUserForDelete()
    	userService.demand.deleteUser(){User user -> }
    	controller.userService = userService.createMock()
    	mockParams.id = userAdmin.id
    	
    	controller.delete()
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.deleted.message', controller.flash.message
    }
    
    void testDeleteRootAccount() {
    	setUpUserForDelete()
    	mockParams.id = 1
    	
    	controller.delete()
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.admin.root.noDelete.message', controller.flash.message
    }
    
    void testDeleteOwnAccount() {
    	userSuper.id = 5
    	userSuper.save(flush:true)
    	setUpUserForDelete()
    	mockParams.id = userSuper.id
    	
    	controller.delete()
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'tf.admin.self.noDelete.message', controller.flash.message
    }
    
    void testDeleteNonExistingAccount() {
    	setUpUserForDelete()
    	mockParams.id = 999
    	
    	controller.delete()
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
}
