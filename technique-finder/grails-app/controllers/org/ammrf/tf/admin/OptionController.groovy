package org.ammrf.tf.admin

import org.ammrf.tf.Option;
import org.ammrf.tf.OptionType;
import org.ammrf.tf.Science;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * Admin controller to list/view/edit/delete options for Biology and Physical sciences
 * 
 * @author Andrey Chernyshov
 *
 */
@Secured(['ROLE_ADMIN'])
class OptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
    
    def defaultAction = "list"
    
    def optionService
    
    def nonExistingOptionRedirectionParams = [science:Science.BIOLOGY, type:OptionType.LEFT]
    
    /**
     * Lists options sorted by priority ascending (hight to low)
     */
    def list = {
        Science science = Science.valueOf(params.science)
        OptionType type = OptionType.valueOf(params.type)
        [optionInstanceList: Option.findAll("from Option as o where o.science=:science and o.type=:type order by o.priority", [science:science, type:type])]
    }
    
    def create = {
        def optionInstance = new Option()
        optionInstance.properties = params
        optionInstance.priority = optionService.getMaxPriority(Science.valueOf(params.science), OptionType.valueOf(params.type)) + 1
        log.debug "Priority for new option set to $optionInstance.priority"
        return [optionInstance: optionInstance]
    }

    def save = {
        def optionInstance = new Option(params)
        if (optionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'option.label', default: 'Option'), optionInstance.name])}"
            redirect(action: "list", params: [science:optionInstance.science, type:optionInstance.type])
        }
        else {
            render(view: "create", model: [optionInstance: optionInstance])
        }
    }

    def edit = {
        def optionInstance = Option.get(params.id)
        if (!optionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.label', default: 'Option'), params.id])}"
            redirect(action: "list", params: nonExistingOptionRedirectionParams)
        }
        else {
            return [optionInstance: optionInstance]
        }
    }

    def update = {
        def optionInstance = Option.get(params.id)
        if (optionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (optionInstance.version > version) {
                    
                    optionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'option.label', default: 'Option')] as Object[], "Another user has updated this Option while you were editing")
                    render(view: "edit", model: [optionInstance: optionInstance])
                    return
                }
            }
            optionInstance.name = params.name
            if (!optionInstance.hasErrors() && optionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'option.label', default: 'Option'), optionInstance.name])}"
				flash.updatedObjectId = optionInstance.id
                redirect(action: "list", params: [science:optionInstance.science, type:optionInstance.type])
            }
            else {
                render(view: "edit", model: [optionInstance: optionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.label', default: 'Option'), params.id])}"
            redirect(action: "list", params: nonExistingOptionRedirectionParams)
        }
    }

    def delete = {
        def optionInstance = Option.get(params.id)
        if (optionInstance) {
            try {
                optionService.delete(optionInstance)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'option.label', default: 'Option'), optionInstance.name])}"
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'option.label', default: 'Option'), optionInstance.name])}"
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.label', default: 'Option'), params.id])}"
        }
        
        if(optionInstance) {
	    	redirect(action: "list", params: [science:optionInstance.science, type:optionInstance.type])
	    } else {
	    	redirect(action: "list", params: nonExistingOptionRedirectionParams)
	    }
    }
    
    /**
     * Changes option priority (moves option one position up/down in the list)
     */
    private def move = {direction, params ->
	    def optionInstance = Option.get(params.id)
		if (optionInstance) {
	        try {
	        	switch(direction) {
	        		case 'Up': 
	        			optionService.moveUp(optionInstance)
	        			break
	        		case 'Down':
	        			optionService.moveDown(optionInstance)
	        			break
	        		default:
	        			throw new IllegalArgumentException(direction  + " is not supported. Use 'Up' or 'Down' only.")
	        	}
	            flash.message = "${message(code: "tf.object.moved${direction}.message", args: [message(code: 'option.label', default: 'Option'), optionInstance.name])}"
	        }
	        catch (IllegalArgumentException e) {
	            flash.message = "${message(code: "tf.object.not.moved${direction}.message", args: [message(code: 'option.label', default: 'Option'), optionInstance.name])}"
	        }
	        catch (OptimisticLockingFailureException ex) {
	        	log.error ex
	        	flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'option.label', default: 'Option')])}"
	        }
	    }
	    else {
	        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.label', default: 'Option'), params.id])}"
	    }
	    
	    if(optionInstance) {
	    	redirect(action: "list", params: [science:optionInstance.science, type:optionInstance.type])
	    } else {
	    	redirect(action: "list", params: nonExistingOptionRedirectionParams)
	    }
    }
    
    /**
     * Moves option one position up the list
     */
    def moveUp = {
    	move('Up', params)
    }
    
    /**
     * Moves option one position down the list
     */
    def moveDown = {
    	move('Down', params)
    }
}
