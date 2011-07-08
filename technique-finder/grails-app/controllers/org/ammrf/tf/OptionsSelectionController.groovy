package org.ammrf.tf

/**
 * Controller to list available options for biology and physics
 * (left/right choice selection public page)
 * 
 * @author Andrey Chernyshov
 */
class OptionsSelectionController {
	static allowedMethods = [listBioOptions: "GET", listPhysicsOptions: "GET"]
	                         
	def optionService
	def staticContentService
	
	protected def listOptions = {Science science ->
		def key = (science == Science.BIOLOGY ? 'biologyChoices' : 'physicsChoices') 
		
		def quickGuide = staticContentService.findText('tf.' + key + '.quickGuide')
		def leftOptionsTitle = staticContentService.findText('tf.' + key + '.left.title')
		def rightOptionsTitle = staticContentService.findText('tf.' + key +'.right.title')
		def comparisonTitle = staticContentService.findText('tf.' + key + '.comparison.title')?.toUpperCase()
		
        def leftOption = optionService.getOptions(science, OptionType.LEFT)
        def rightOption = optionService.getOptions(science, OptionType.RIGHT)
        render(view:'index', 
        	   model:[quickGuide:quickGuide, 
        	          leftOptionsTitle:leftOptionsTitle, 
        	          rightOptionsTitle:rightOptionsTitle,
        	          comparisonTitle:comparisonTitle,
        	          leftOptions:leftOption, 
        	          rightOptions:rightOption,
        	          science:science
        	   ]
        )
    }
	
	def listBioOptions = {
		listOptions(Science.BIOLOGY)
    }
	
	def listPhysicsOptions = {
		listOptions(Science.PHYSICS)
	}
	
}
