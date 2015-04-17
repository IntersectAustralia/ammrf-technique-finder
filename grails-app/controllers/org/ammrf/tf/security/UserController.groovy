package org.ammrf.tf.security

import org.codehaus.groovy.grails.plugins.springsecurity.Secured;
import org.springframework.security.AccessDeniedException;

@Secured(['ROLE_ADMIN'])
class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
    
    def defaultAction = "list"
                            	 
    Long ROOT_SUPER_ACCOUNT_ID = 1
    String DEFAULT_PASSWORD = 'admin'
    
    def authenticateService
    def userService
    
    /**
     * Returns user by provided id.
     * Redirects to 'access denied' page if current user is not ADMIN_SUPER and provided id is not his one.
     * ADMIN_SUPER user can access all users in the system.
     */
    private def getUser = { userId ->
    	def isSuper = authenticateService.ifAllGranted('ROLE_SUPER')
    	if(isSuper || authenticateService.userDomain()?.id.equals(Long.valueOf(userId))) {
    		def userInstance = User.get(userId)
    		if (!userInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), userId])}"
	            redirect(action: "list")
	        } else {
	        	def superRole = Role.findByAuthority("ROLE_SUPER")
	        	userInstance.isSuper = superRole.people.contains(userInstance)
	        	userInstance.secondEmailConfirmation = userInstance.secondEmail
	        	return userInstance
	        }
    	} else {
    		redirect(controller:"login", action:"denied")
    	}
    }
    
    @Secured(['ROLE_SUPER'])
    def list = {
        def superRole = Role.findByAuthority("ROLE_SUPER")
        def userList = User.list(sort:(params.sort ? params.sort : "username"), order:params.order).each{it.isSuper = superRole.people.contains(it)}
        [userInstanceList: userList]
    }
    
    @Secured(['ROLE_SUPER'])
    def create = {
    	def userInstance = new User()
        userInstance.properties = params
        return [userInstance: userInstance]
    }
    
    @Secured(['ROLE_SUPER'])
    def save = {
        def userInstance = new User(params)
        userInstance.passwd = authenticateService.encodePassword(DEFAULT_PASSWORD)
        
        if (userService.createUser(userInstance)) {
            flash.message = "${message(code: 'user.create.message', args: [userInstance.username, DEFAULT_PASSWORD])}"
            redirect(action: "show", id: userInstance.id, params:[superAdmin:true])
        }
        else {
            render(view: "create", model: [userInstance: userInstance])
        }
    }
    
    def show = {
        def userInstance = getUser(params.id)
        if (userInstance) {
            [userInstance: userInstance, superAdmin:params.superAdmin]
        }
    }
    
    def edit = {
    	def userInstance = getUser(params.id)
        if (userInstance) {
            [userInstance: userInstance]
        }
    }
    
    @Secured(['ROLE_SUPER'])
    def editAsSuper = {
    	def userInstance = getUser(params.id)
        if (userInstance) {
        	def superRole = Role.findByAuthority("ROLE_SUPER")
        	userInstance.isSuper = superRole.people.contains(userInstance)
            [userInstance: userInstance]
        }
    }
    
    @Secured(['ROLE_SUPER'])
    def updateAsSuper = {
    	
    	def userInstance = getUser(params.id)
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {
                    
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "editAsSuper", model: [userInstance: userInstance])
                    return
                }
            }
            
            userInstance.fullName = params.fullName
            userInstance.isSuper = params.isSuper ? true : false
            
            if(userService.updateUserAndRoles(userInstance)) {
            	flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
            	redirect(action: "show", id: userInstance.id, params:[superAdmin:true])
            } else {
                render(view: "editAsSuper", model: [userInstance: userInstance])
            }
        }
    }

    
    def update = {
        def userInstance = getUser(params.id)
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {
                    
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }
            
            userInstance.fullName = params.fullName
            if(params.password) {
            	userInstance.password = params.password
            	userInstance.passwordConfirmation = params.passwordConfirmation
            	userInstance.passwd = authenticateService.encodePassword(params.password)
            }
            
            userInstance.secondEmail = params.secondEmail.trim()
            userInstance.secondEmailConfirmation = params.secondEmailConfirmation.trim()
            
            if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
                redirect(action: "show", id: userInstance.id)
            } else {
                render(view: "edit", model: [userInstance: userInstance])
            }
        }
    }
    
    @Secured(['ROLE_SUPER'])
    def delete = {
    	if(ROOT_SUPER_ACCOUNT_ID.equals(Long.valueOf(params.id))) {
    		flash.message = "${message(code: 'tf.admin.root.noDelete.message')}"
            redirect(action: "list")
            return
    	}
    	
    	if(authenticateService.userDomain()?.id.equals(Long.valueOf(params.id))) {
    		flash.message = "${message(code: 'tf.admin.self.noDelete.message')}"
            redirect(action: "list")
            return
    	}
    	
        def userInstance = User.get(params.id)
        if (userInstance) {
        	def username = userInstance.username
            try {
            	userService.deleteUser(userInstance)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), username])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), username])}"
                redirect(action: "list")
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }
}
