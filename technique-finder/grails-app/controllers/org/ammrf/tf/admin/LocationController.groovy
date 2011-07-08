package org.ammrf.tf.admin


import org.ammrf.tf.Location;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * Admin controller to list/view/edit/delete locations (AMMRF nodes)
 * 
 * @author Andrey Chernyshov
 *
 */
@Secured(['ROLE_ADMIN'])
class LocationController {

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
                           
    def scaffold = Location
    
    def locationService                         

    /**
     * Lists locations sorted by priority ascending (hight to low)
     */
    def list = {
        [locationInstanceList: Location.list(sort:'priority')]
    }

    def create = {
        def locationInstance = new Location()
        locationInstance.properties = params
        locationInstance.priority = locationService.getMaxPriority() + 1
        log.debug "Priority for new location set to $locationInstance.priority"
        return [locationInstance: locationInstance]
    }

    def delete = {
        def locationInstance = Location.get(params.id)
        if (locationInstance) {
            try {
                locationService.delete(locationInstance)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.institution])}"
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.institution])}"
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
        }
        redirect(action: "list")
    }
    
    /**
     * Changes location priority (moves location one position up/down in the list)
     */
    private def move = {direction, params ->
	    def locationInstance = Location.get(params.id)
		if (locationInstance) {
	        try {
	        	switch(direction) {
	        		case 'Up': 
	        			locationService.moveUp(locationInstance)
	        			break
	        		case 'Down':
	        			locationService.moveDown(locationInstance)
	        			break
	        		default:
	        			throw new IllegalArgumentException(direction  + " is not supported. Use 'Up' or 'Down' only.")
	        	}
	            flash.message = "${message(code: "tf.object.moved${direction}.message", args: [message(code: 'location.label', default: 'Location'), locationInstance.institution])}"
	        }
	        catch (IllegalArgumentException e) {
	            flash.message = "${message(code: "tf.object.not.moved${direction}.message", args: [message(code: 'location.label', default: 'Location'), locationInstance.institution])}"
	        }
			catch (OptimisticLockingFailureException ex) {
	        	log.error ex
	        	flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'location.label', default: 'Location')])}"
	        }
	    }
	    else {
	        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
	    }
		redirect(action: "list")
    }
    
    /**
     * Moves location one position up the list
     */
    def moveUp = {
    	move('Up', params)
    }
    
    /**
     * Moves location one position down the list
     */
    def moveDown = {
    	move('Down', params)
    }
}
