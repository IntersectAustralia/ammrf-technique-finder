package org.ammrf.tf

import grails.test.*

class HomePageControllerTests extends ControllerUnitTestCase {
	def quickGuide
	def optionsExplanation
	def searchExplanation
	def allTechniquesExplanation
	def infoboxContent
		
	protected void setUp() {
		super.setUp()
		quickGuide = 'Test quick guide text'
		optionsExplanation = 'Test options explanation text'
		searchExplanation = 'Test search explanation text'
		allTechniquesExplanation = 'Test all techniques explanation text'
		infoboxContent = 'Infobox content'
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	void testStaticContentText() {
		def staticContentService = mockFor(StaticContentService)
		staticContentService.demand.findText(1..5) {String key ->
			switch(key) {
				case 'tf.home.quickGuide': 
					return quickGuide
					break
				case 'tf.home.optionsExplanation': 
					return optionsExplanation 
					break
				case 'tf.home.searchExplanation': 
					return searchExplanation 
					break
				case 'tf.home.allTechniquesExplanation':
					return allTechniquesExplanation
					break
				case 'tf.home.infoboxContent':
					return infoboxContent
					break
				default: ''
			}
		}
		controller.staticContentService = staticContentService.createMock()
		
		def model = controller.index()
		assertEquals 'Quick guide text is incorrect', quickGuide, model.quickGuide
		assertEquals 'Options explanation text is incorrect', optionsExplanation, model.optionsExplanation
		assertEquals 'All techniques explanation text is incorrect', allTechniquesExplanation, model.allTechniquesExplanation
		assertEquals 'Search explanation text is incorrect', searchExplanation, model.searchExplanation
		assertEquals 'Infobox content is incorrect', infoboxContent, model.infoboxContent
	}
}
