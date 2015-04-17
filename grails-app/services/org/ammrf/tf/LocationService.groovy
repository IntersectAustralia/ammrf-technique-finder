package org.ammrf.tf

/**
 * Service to work with locations
 * 
 * @author Andrey Chernyshov
 *
 */
class LocationService {

    boolean transactional = true

    /**
     * Returns current max priority in the DB
     * 
     * @return current max priority or 0 if there is no locations in DB
     */
    def getMaxPriority() {
    	def maxPriority = Location.createCriteria().get {
    		projections {
    			max('priority')
    		}
    	}
    	return maxPriority ? maxPriority : 0
    }
    
    def delete(Location location) {
    	Location.executeUpdate("update Location set priority = priority-1 where priority > :priority", [priority:location.priority])
    	location.delete(flush: true)
    }
    
    def moveUp(Location location) {
    	if(location.priority == 1) {
    		throw new IllegalArgumentException('Can not move up top-most location')
    	}
    	
    	def prevLocation = Location.findByPriority(location.priority - 1)
    	prevLocation.priority++
    	location.priority--
    }
    
    def moveDown(Location location) {
    	def nextLocation = Location.findByPriority(location.priority + 1)
    	if(nextLocation) {
    		nextLocation.priority--
    	} else {
    		throw new IllegalArgumentException('Can not move down lowerst location')
    	}
    	location.priority++
    }
}
