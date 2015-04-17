package org.ammrf.tf.security

import org.ammrf.tf.Util;
import grails.test.*

class UserTests extends GrailsUnitTestCase {
	def user
    protected void setUp() {
        super.setUp()
        user = new User(username:"test@ammrf.org.au", fullName:"Test user", isSuper:false, 
        		passwd:"test", password:"unused", passwordConfirmation:"unused", secondEmail:'', secondEmailConfirmation:'')
        mockDomain(User, [user])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUserNameBlank() {
    	user.username = ''
    	Util.checkFieldBlank(this, user, 'username')
    }
    
    void testUserNameUnique() {
    	def user2 = new User(username:user.username, fullName:"Test user 2", isSuper:false, 
        		passwd:"test", password:"unused", passwordConfirmation:"unused", secondEmail:'', secondEmailConfirmation:'')
    	assertFalse 'Validation should have failed', user2.validate()
    	assertEquals 'unique', user2.errors.username
    }
    
    void testUserNameIsEmail() {
    	Util.checkFieldIsEmail(this, user, 'username')
    }
    
    void testUserNameMaxSize() {
    	Util.checkFieldLength(this, user, 'username', 255, '@test.com')
    }
    
    void testFullNameBlank() {
    	user.fullName = ''
    	Util.checkFieldBlank(this, user, 'fullName')
    }
    
    void testFullNameMaxSize() {
    	Util.checkFieldLength(this, user, 'fullName', 255)
    }
    
    void testPasswdBlank() {
    	user.passwd = ''
    	Util.checkFieldBlank(this, user, 'passwd')
    }
    
    void testPasswdMaxSize() {
    	Util.checkFieldLength(this, user, 'passwd', 255)
    }
    
    void testPasswordBlank() {
    	user.password = ''
    	Util.checkFieldBlank(this, user, 'password')
    }
    
    void testPasswordMaxSize() {
    	Util.checkFieldLength(this, user, 'password', 255)
    }
    
    void testPasswordConfirmationBlank() {
    	user.passwordConfirmation = ''
    	Util.checkFieldBlank(this, user, 'passwordConfirmation')
    }
    
    void testPasswordConfirmationMaxSize() {
    	Util.checkFieldLength(this, user, 'passwordConfirmation', 255)
    }
    
    void testPasswordConfirmationMatchesPassword() {
    	user.password = 'testpwd'
    	user.passwordConfirmation = user.password + '1'
    	assertFalse 'Validation should have failed', user.validate()
    	assertEquals 'tf.user.password.notMatch', user.errors.passwordConfirmation
    }
    
    void testSecondEmailIsEmail() {
    	Util.checkFieldIsEmail(this, user, 'secondEmail')
    }
    
    void testSecondEmailMaxSize() {
    	Util.checkFieldLength(this, user, 'secondEmail', 255, '@test.com')
    }
    
    void testSecondEmailConfirmationIsEmail() {
    	Util.checkFieldIsEmail(this, user, 'secondEmailConfirmation')
    }
    
    void testSecondEmailConfirmationMaxSize() {
    	Util.checkFieldLength(this, user, 'secondEmailConfirmation', 255, '@test.com')
    }
    
    void testSecondEmailConfirmationMatchesSecondEmail() {
    	user.secondEmail = 'test@test.com'
    	user.secondEmailConfirmation = '1' + user.secondEmail
    	assertFalse 'Validation should have failed', user.validate()
    	assertEquals 'tf.user.secondEmail.notMatch', user.errors.secondEmailConfirmation
    }
    
    void testSecondEmailNotEqualUsername() {
    	user.secondEmail = user.username
    	assertFalse 'Validation should have failed', user.validate()
    	assertEquals 'tf.user.secondEmail.equalsUsername', user.errors.secondEmail
    }
    
}
