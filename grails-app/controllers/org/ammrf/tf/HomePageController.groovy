package org.ammrf.tf

class HomePageController {
    static allowedMethods = [index: "GET"]

    def staticContentService

    def index = {
	    def quickGuide = staticContentService.findText('tf.home.quickGuide')
	    def optionsExplanation = staticContentService.findText('tf.home.optionsExplanation')
	    def searchExplanation = staticContentService.findText('tf.home.searchExplanation')
		def allTechniquesExplanation = staticContentService.findText('tf.home.allTechniquesExplanation')
		def infoboxContent = staticContentService.findText('tf.home.infoboxContent')
	    [quickGuide:quickGuide, optionsExplanation:optionsExplanation, searchExplanation:searchExplanation, 
		 allTechniquesExplanation:allTechniquesExplanation, infoboxContent:infoboxContent]
    }
}
