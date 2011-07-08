import org.ammrf.tf.security.User

import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

/**
 * Reset password controller
 */
class ResetController {

	def emailConfirmationService

	def authenticateService

	static allowedMethods = [reset: "POST", update: "POST"]

	def index = {
		redirect (action: "resetStep1")
	}

        def resetStep1 = {
        }

	def reset = {
		def user = User.findByUsername(params.username)
                if (user) {
			def destEmail = null;
			try {
				destEmail = user.username
				emailConfirmationService.sendConfirmation(user.username, "Reset password email for TF", [view:"resetEmail"], "1.reset/resetPassword")
				if (user.secondEmail) {
					destEmail = user.secondEmail
					emailConfirmationService.sendConfirmation(user.secondEmail, "Reset password email for TF", [view:"resetEmail"], "2.reset/resetPassword")
				}
		                redirect (action: "resetStep2")
			} catch(Throwable t) {
				log.error "Could not sent $destEmail", t
				flash.message = "Email sending failed, if error persist please contact administrators."
				redirect(action:"resetStep1")
			}
                } else {
			flash.message = message(code:'tf.reset.usernotfound', args:[params.username])
			render (view: "index")
                }
        }

        def resetStep2 = {
        }

	@Secured(['ROLE_ADMIN'])
        def resetPassword = {
		def user = User.findById(params.id)
		if (authenticateService.principal()?.username?.equals(user?.username)) {
			return [userInstance:User.findById(params.id)]
		} else {
			redirect(controller:"login", action:"denied")
		}
        }

	@Secured(['ROLE_ADMIN'])
	def update = {
        	def userInstance = User.findById(params.id)
		if (!authenticateService.principal()?.username?.equals(userInstance.username)) {
			redirect(controller:"login", action:"denied")
			return
		}
        	if (userInstance) {
            		if (params.version) {
                		def version = params.version.toLong()
                		if (userInstance.version > version) {
                    
                    			userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
	                    		render(view: "resetPassword", model: [userInstance: userInstance])
        	            		return
                		}
            		}
            
            		userInstance.password = params.password
            		userInstance.passwordConfirmation = params.passwordConfirmation
            		userInstance.passwd = authenticateService.encodePassword(params.password)
			userInstance.secondEmailConfirmation = userInstance.secondEmail
            
			if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
			        flash.message = "${message(code: 'tf.reset.updated')}"
			        redirect(controller: "admin")
				return
			} else {
			        render(view: "resetPassword", model: [userInstance: userInstance])
				return
			}
		} else {
			redirect(controller:"login", action:"denied")
			return
                }
        }
 
}
