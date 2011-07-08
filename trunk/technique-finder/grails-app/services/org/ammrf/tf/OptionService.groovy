package org.ammrf.tf

/**
 * Service to work with options (bio and physics)
 * 
 * @author Andrey Chernyshov
 *
 */
class OptionService {

    boolean transactional = true

    /**
     * Returns current max priority for options of the specified science and type in the DB
     * 
     * @param science science this option is for
     * @param type type of the option (left or rignt coulmn)
     * 
     * @return current max priority or 0 if there is no options of the specified science and type in DB
     */
    def getMaxPriority(Science science, OptionType type) {
    	def maxPriority = Option.createCriteria().get {
    		and {
    			eq('science', science)
    			eq('type', type)
    		}
    		projections {
    			max('priority')
    		}
    	}
    	return maxPriority ? maxPriority : 0
    }
    
    def delete(Option option) {
    	Option.executeUpdate("update Option set priority = priority-1 where science = :science and type = :type and priority > :priority", 
    			[science:option.science, type:option.type, priority:option.priority])
    	option.delete(flush: true)
    }
    
    def moveUp(Option option) {
    	if(option.priority == 1) {
    		throw new IllegalArgumentException('Can not move up top-most option')
    	}
    	
    	def prevOption = Option.createCriteria().get {
    		and {
    			eq('science', option.science)
    			eq('type', option.type)
    			eq('priority', option.priority - 1)
    		}
    	}
    	prevOption.priority++
    	option.priority--
    }
    
    def moveDown(Option option) {
    	def nextOption = Option.createCriteria().get {
    		and {
    			eq('science', option.science)
    			eq('type', option.type)
    			eq('priority', option.priority + 1)
    		}
    	}
    	if(nextOption) {
    		nextOption.priority--
    	} else {
    		throw new IllegalArgumentException('Can not move down lowerst option')
    	}
    	option.priority++
    }
    
    def getOptions(Science science, OptionType type) {
    	return Option.findAllByScienceAndType(science, type, [sort:"priority"])
    }
}
