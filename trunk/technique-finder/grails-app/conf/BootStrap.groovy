import java.io.File;

import grails.util.GrailsUtil;
import groovy.sql.Sql;

import org.springframework.security.providers.UsernamePasswordAuthenticationToken
import org.springframework.security.GrantedAuthorityImpl
import org.springframework.security.GrantedAuthority
import org.springframework.security.context.SecurityContextHolder
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserImpl
import org.codehaus.groovy.grails.commons.ConfigurationHolder


import org.ammrf.tf.*;
import org.ammrf.tf.security.*;


class BootStrap {
	
	def dataSource
	def emailConfirmationService
	def authenticateService
	
	def init = { servletContext ->

		if (ConfigurationHolder.config.emailConfirmation.maxDays) {
			emailConfirmationService.maxAge = Long.valueOf(ConfigurationHolder.config.emailConfirmation.maxDays) * 1000 * 60 * 60 * 24
		}

		emailConfirmationService.onConfirmation = { email, action ->

			def option = Integer.valueOf(action.substring(0,1))

			action = action.substring(2)

			def user = option == 1 ? User.findByUsername(email) : User.findBySecondEmail(email)
                        if (user) {
				GrantedAuthority[] auths = user.authorities.collect { new GrantedAuthorityImpl(it.authority) }
				def grailsUser = new GrailsUserImpl(user.username, 'unknown', true, true, true, true, auths, user)
				SecurityContextHolder.context.authentication = new UsernamePasswordAuthenticationToken(grailsUser, 'unknown', auths)
				def comms = action.split("/")
				return [controller:comms[0], action:comms[1], id: user.id]
                        } else {
				flash.message = "User not longer exists"
                                return [controller:'login',action:'denied']
                        }

		}

		emailConfirmationService.onInvalid = { uid -> 
			log.warn("User with id $uid failed")
                        flash.message = "User with id $uid failed to confirm"
                        return [controller:'login', action:'invalid']
		}

		emailConfirmationService.onTimeout = { email, uid -> 
			log.warn("Request experied")
                        flash.message = "User with id $uid timed out"
                        return [controller:'login', action:'invalid']
		}

	 	if(grails.util.GrailsUtil.environment == 'development') {
	 		// populating additional data
	 		log.debug "Populating dev specific data"
	 		
	 		// associating techniques with options
	 		if(OptionCombination.count() == 0) {
				new OptionCombination(priority:1, left:Option.get(1), right:Option.get(18), technique:Technique.get(2)).save()
	  			new OptionCombination(priority:2, left:Option.get(1), right:Option.get(22), technique:Technique.get(2)).save()
	  			new OptionCombination(priority:1, left:Option.get(1), right:Option.get(22), technique:Technique.get(3)).save()
	  			new OptionCombination(priority:1, left:Option.get(2), right:Option.get(21), technique:Technique.get(3)).save()
	  			
	  			new OptionCombination(priority:1, left:Option.get(43), right:Option.get(40), technique:Technique.get(2)).save()
	 		}
	 		
  			//populating contacts
	 		if(Contact.count() == 0) {
				new Contact(title:'Dr', name:'B Test', position:'test', email:'test@ammrf.org.au', techniqueContact:true, location:Location.findByPriority(1), telephone:'02 8888 9999').save()
	  			new Contact(title:'Mr', name:'A Test', position:'test 2', email:'test@ammrf.org.au', techniqueContact:false, location:Location.findByPriority(2), telephone:'02 8899 6677').save()
	  			new Contact(title:'Dr', name:'C Test', position:'instrument scientist', email:'test@ammrf.org.au', techniqueContact:true, location:Location.findByPriority(3)).save()
	 		}
	  	}
	  }
     
     def destroy = {
     }
} 
