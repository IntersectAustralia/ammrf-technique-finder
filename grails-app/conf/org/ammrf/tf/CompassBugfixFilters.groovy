package org.ammrf.tf

/**
 * Workaround Filter to fix compass bug (updates don't update index)
 * 
 * @author Andrey Chernyshov
 *
 */
class CompassBugfixFilters {
	
	def techniqueService
	
    def filters = {
        techniqueUpdated(controller:'techniqueAdmin', action:'show') {
            after = {model ->
            	log.trace "Reindex filter was called for controller=$controllerName"
            	if(flash.message && model?.instance) {
            		reindexTechniques([model.instance])
            	}
            }
        }
        
        referencedObjectUpdated(controller:"(contact|review|location)", action:"show") {
            after = {model ->
            	log.trace "Reindex filter was called for controller=$controllerName"
            	def instance = model?."${controllerName + 'Instance'}"
            	if(flash.message && instance) {
            		log.debug "Refrenced object [$instance] was updated. Reindexing linked techniques"
            		def techniques = techniqueService.findTechniquesByRelation(instance)
            		reindexTechniques(techniques)
            	}
            }
        }
        
        caseStudyUpdated(controller:"caseStudy", action:"list") {
            after = {model ->
            	log.trace "Reindex filter was called for controller=$controllerName"
            	if(flash.message && flash.updatedObjectId) {
            		def instance = CaseStudy.get(flash.updatedObjectId)
            		log.debug "Refrenced object [$instance] was updated. Reindexing linked techniques"
            		def techniques = techniqueService.findTechniquesByRelation(instance)
            		reindexTechniques(techniques)
            	}
            }
        }
		
		caseOptionUpdated(controller:"option", action:"list") {
			after = {model ->
				log.trace "Reindex filter was called for controller=$controllerName"
				if(flash.message && flash.updatedObjectId) {
					def instance = Option.get(flash.updatedObjectId)
					log.debug "Refrenced object [$instance] was updated. Reindexing linked techniques"
					def techniques = techniqueService.findTechniquesByRelation(instance)
					reindexTechniques(techniques)
					Set optionCombinations = [] 
					techniques.each{it.options.each{
							if(instance.equals(it.left) || instance.equals(it.right)) {
								optionCombinations += it
							}
						}
					}
					log.debug "Reindexing OptionCombination objects $optionCombinations"
					optionCombinations.each{it.reindex()}
				}
			}
		}
		
		mediaUpdated(controller:"media", action:"show") {
			after = {model ->
				log.trace "Reindex filter was called for controller=$controllerName"
				def instance = model?.instance
				def techniques = model?.techniques
				if(flash.message && instance && techniques) {
					log.debug "Refrenced object [$instance] was updated. Reindexing linked techniques"
					reindexTechniques(techniques)
					def mediaInSections = techniques.medias
					log.debug "Reindexing MediaInSection objects $mediaInSections"
					mediaInSections.each{it.each {it.reindex()}}
				}
			}
		}
        
    }
    
    private def reindexTechniques(techniques) {
    	log.debug "Reindexing techniques $techniques"
    	techniques.each {it.reindex()}
    }
    
}
