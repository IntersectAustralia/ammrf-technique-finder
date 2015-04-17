package org.ammrf.tf

class CacheFilters {
	
    def filters = {
    	disableBrowserCache(controller:'optionsAssociation', action:'listAssociatedTechniques') {
            before = {
            	log.debug "Setting cache headers to clear browser cache"
            	ControllerUtils.disableBrowserCache(response)
            }
        }
    }
    
}
