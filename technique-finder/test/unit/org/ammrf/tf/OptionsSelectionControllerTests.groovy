package org.ammrf.tf

import grails.test.*

class OptionsSelectionControllerTests extends ControllerUnitTestCase {
	def optionService
	def staticContentService
	
	def bioQuickGuide = 'Bio quick guide'
	def bioLeftOptionsTitle = 'Bio step1'
	def bioRightOptionsTitle = 'Bio step2'
	def bioComparisonTitle = 'and there'

	def physicsQuickGuide = 'Physics quick guide'
	def physicsLeftOptionsTitle = 'Physics step1'
	def physicsRightOptionsTitle = 'Physics step2'
	def physicsComparisonTitle = 'at the scale'

	def bioLeftOption
	def bioRightOptions
	
	def physicsLeftOption
	def physicsRightOptions
	
    protected void setUp() {
        super.setUp()
        def optionService = mockFor(OptionService)
        def staticContentService = mockFor(StaticContentService)
        staticContentService.demand.findText(4..4) {String key ->
        	switch (key) {
				case 'tf.biologyChoices.quickGuide':
					return bioQuickGuide
					break
				case 'tf.biologyChoices.comparison.title':
					return bioComparisonTitle
					break
				case 'tf.biologyChoices.left.title':
					return bioLeftOptionsTitle
					break
				case 'tf.biologyChoices.right.title':
					return bioRightOptionsTitle
					break
					
				case 'tf.physicsChoices.quickGuide':
					return physicsQuickGuide
					break
				case 'tf.physicsChoices.comparison.title':
					return physicsComparisonTitle
					break
				case 'tf.physicsChoices.left.title':
					return physicsLeftOptionsTitle
					break
				case 'tf.physicsChoices.right.title':
					return physicsRightOptionsTitle
					break
			}
        }
        controller.staticContentService = staticContentService.createMock()
        
        bioLeftOption = []
        bioRightOptions = []
        physicsLeftOption = []
        physicsRightOptions = []
                     
        for(i in 1..3) {
        	bioLeftOption += new Option(priority:i, name: "left bio option $i", science: Science.BIOLOGY, type:OptionType.LEFT)
        	bioRightOptions += new Option(priority:i, name: "right bio option $i", science: Science.BIOLOGY, type:OptionType.RIGHT)
        	
        	physicsLeftOption += new Option(priority:i, name: "left physics option $i", science: Science.PHYSICS, type:OptionType.LEFT)
        	physicsRightOptions += new Option(priority:i, name: "right physics option $i", science: Science.PHYSICS, type:OptionType.RIGHT)
        }
        optionService.demand.getOptions(2..2) {Science science, OptionType type -> 
        	switch (science) {
				case Science.BIOLOGY:
					return type == OptionType.LEFT ? bioLeftOption : bioRightOptions
					break;
				case Science.PHYSICS:
					return type == OptionType.LEFT ? physicsLeftOption : physicsRightOptions
					break;
			}
        }
        controller.optionService = optionService.createMock()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListBioOptions() {
		def model = controller.listBioOptions()
		assertEquals 'Quick guide text is incorrect', bioQuickGuide, model.quickGuide
		assertEquals 'Left options title text is incorrect', bioLeftOptionsTitle, model.leftOptionsTitle
		assertEquals 'Right options title text is incorrect', bioRightOptionsTitle, model.rightOptionsTitle
		assertEquals 'Comparison title text is incorrect', bioComparisonTitle.toUpperCase(), model.comparisonTitle
		
		assertEquals 'Left options are incorrect', bioLeftOption, model.leftOptions
		assertEquals 'Right options are incorrect', bioRightOptions, model.rightOptions
		
		assertEquals 'View is incorrect', 'index', renderArgs.view
    }
    
    void testListPhysicsOptions() {
		def model = controller.listPhysicsOptions()
		assertEquals 'Quick guide text is incorrect', physicsQuickGuide, model.quickGuide
		assertEquals 'Left options title text is incorrect', physicsLeftOptionsTitle, model.leftOptionsTitle
		assertEquals 'Right options title text is incorrect', physicsRightOptionsTitle, model.rightOptionsTitle
		assertEquals 'Comparison title text is incorrect', physicsComparisonTitle.toUpperCase(), model.comparisonTitle
		
		assertEquals 'Left options are incorrect', physicsLeftOption, model.leftOptions
		assertEquals 'Right options are incorrect', physicsRightOptions, model.rightOptions
		
		assertEquals 'View is incorrect', 'index', renderArgs.view
    }
}
