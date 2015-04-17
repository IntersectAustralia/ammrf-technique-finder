package org.ammrf.tf

import java.util.List;

import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.compass.core.engine.SearchEngineQueryParseException;
import grails.test.*

class SearchResult {
	List<Technique> results
}

class TechniqueControllerTests extends ControllerUnitTestCase {
	def techniqueService
	def staticContentService
	def techniques
	def leftOption
	def rightOption
	def technique
	def bioComparisonTitle = 'and there'
	def physicsComparisonTitle = 'at the scale'
    
	protected void setUp() {
        super.setUp()
        leftOption = new Option(priority:1, name:"Test left option", science:Science.BIOLOGY, type:OptionType.LEFT)
        rightOption = new Option(priority:1, name:"Test right option", science:Science.BIOLOGY, type:OptionType.RIGHT)
        mockDomain(Option, [leftOption, rightOption])

        techniques = [new Technique(name:'Technique 1', summary:'The summary', description:'The description'),
                      new Technique(name:'Technique 2', summary:'The summary', description:'The description'),
                      new Technique(name:'Technique 3', summary:'The summary', description:'The description')]
        technique = new Technique(name:'Technique with description', summary:'The summary', description:'The description')
        mockDomain(Technique, [technique])
        
        TechniqueController.metaClass.message = { Map args -> return args['code']}
        
        techniqueService = mockFor(TechniqueService, true)
        techniqueService.demand.findTechniquesByOptionCombination(){
    		Option left, Option right, List<String> fields -> techniques
    	}
        techniqueService.demand.getMediaInTechniqueSection(2..2){
    		Technique technique, MediaSection mediaSection -> []
    	}
        techniqueService.demand.attachMedia(0..4){
                techniqueList -> []
        }
    	controller.techniqueService = techniqueService.createMock()
    	
    	staticContentService = mockFor(StaticContentService)
    	staticContentService.demand.findText(){String key ->
	    	switch (key) {
				case 'tf.biologyChoices.comparison.title':
					return bioComparisonTitle
					break
				case 'tf.physicsChoices.comparison.title':
					return physicsComparisonTitle
					break
	    	}
    	}
    	controller.staticContentService = staticContentService.createMock()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListBio() {
    	mockParams.leftOption = leftOption.id
    	mockParams.rightOption = rightOption.id
		mockParams.action = "list"
    	def model = controller.list()
    	
    	assertEquals 'Techniques list is incorrect', techniques, model.techniques
    	assertEquals 'Left option is incorrect', leftOption, model.leftOption
    	assertEquals 'Right option is incorrect', rightOption, model.rightOption
    	assertNull 'search should be null', model.search
    	assertNull 'searchResult should be null', model.searchResult
    	assertEquals 'Comparison title text is incorrect', "$leftOption.name $bioComparisonTitle $rightOption.name", model.resultsTitle
		def backCookie = mockResponse.cookies.find {it.name.equals(TechniqueController.BACK_COOKIE_NAME)} 
		assertEquals 'Cookie was not set', "list?leftOption=$leftOption.id&rightOption=$rightOption.id", backCookie?.value
    }
    
    void testListPhysics() {
    	leftOption.science = Science.PHYSICS
    	mockParams.leftOption = leftOption.id
    	mockParams.rightOption = rightOption.id
    	def model = controller.list()
    	
    	assertEquals 'Techniques list is incorrect', techniques, model.techniques
    	assertEquals 'Left option is incorrect', leftOption, model.leftOption
    	assertEquals 'Right option is incorrect', rightOption, model.rightOption
    	assertNull 'search should be null', model.search
    	assertNull 'searchResult should be null', model.searchResult
    	assertEquals 'Comparison title text is incorrect', "$leftOption.name $physicsComparisonTitle $rightOption.name", model.resultsTitle
    }
	
	void testListAll() {
		techniqueService = mockFor(TechniqueService)
		techniqueService.demand.findAllTechniques(){
			List<String> fields -> techniques
		}
		controller.techniqueService = techniqueService.createMock()
		
		mockParams.action = "listAll"
		def model = controller.listAll()
		
		assertEquals 'Techniques list is incorrect', techniques, model.techniques
		def backCookie = mockResponse.cookies.find {it.name.equals(TechniqueController.BACK_COOKIE_NAME)}
		assertEquals 'Cookie was not set', "listAll", backCookie?.value
	}
    
    void testListNonExistingLeftOption() {
    	mockParams.leftOption = '999'
    	mockParams.rightOption = rightOption.id
    	def model = controller.list()

    	assertNull 'Model should be NULL', model
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testListNonExistingRightOption() {
    	mockParams.leftOption = leftOption.id
    	mockParams.rightOption = '999'
    	def model = controller.list()

    	assertNull 'Model should be NULL', model
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    
    void testShow() {
    	mockParams.id = technique.id
    	def model = controller.show()
    	
    	assertEquals 'Technique is incorrect', technique, model.technique
    }
    
    
    void testShowIncorrectId() {
    	mockParams.id = '999'
    	def model = controller.show()

    	assertNull 'Model should be NULL', model
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testSearch() {
    	mockParams.q = "test"
		mockParams.action = "search"
    	def searchResult = new SearchResult(results:techniques)
   		Technique.metaClass.'static'.search = {String query, Map parameters -> return searchResult}
    	
    	controller.search()
    	assertEquals 'Techniques list is incorrect', techniques, renderArgs.model.techniques
    	assertEquals 'Results title list is incorrect', mockParams.q, renderArgs.model.resultsTitle
    	assertTrue 'search should be true', renderArgs.model.search
    	assertEquals 'searchResult shuld be set', searchResult, renderArgs.model.searchResult
    	assertEquals 'View is incorrect', "list", renderArgs.view
		def backCookie = mockResponse.cookies.find {it.name.equals(TechniqueController.BACK_COOKIE_NAME)}
		assertEquals 'Cookie was not set', "search?q=$mockParams.q", backCookie?.value
    }
    
    void testSearchEmptyTerm() {
    	mockParams.q = ""
    	
    	controller.search()
    	assertEquals 'Techniques list is incorrect', [], renderArgs.model.techniques
    	assertEquals 'Results title list is incorrect', mockParams.q, renderArgs.model.resultsTitle
    	assertTrue 'search should be true', renderArgs.model.search
    	assertNull 'searchResult shuld be null', renderArgs.model.searchResult
    	assertEquals 'View is incorrect', "list", renderArgs.view
    }
    
    void testSearchParseException() {
    	mockParams.q = "#@#%^%&*("
   		Technique.metaClass.'static'.search = {String query, Map parameters -> throw new SearchEngineQueryParseException(mockParams.q, new Exception())}
    	
    	controller.search()
    	assertEquals 'Techniques list is incorrect', [], renderArgs.model.techniques
    	assertEquals 'Results title list is incorrect', mockParams.q, renderArgs.model.resultsTitle
    	assertTrue 'search should be true', renderArgs.model.search
    	assertNull 'searchResult shuld be null', renderArgs.model.searchResult
    	assertEquals 'View is incorrect', "list", renderArgs.view
    }
    
    void testSearchTooManyClausesException() {
    	mockParams.q = "*"
   		Technique.metaClass.'static'.search = {String query, Map parameters -> throw new TooManyClauses()}
    	
    	controller.search()
    	assertEquals 'Techniques list is incorrect', [], renderArgs.model.techniques
    	assertEquals 'Results title list is incorrect', mockParams.q, renderArgs.model.resultsTitle
    	assertTrue 'search should be true', renderArgs.model.search
    	assertNull 'searchResult shuld be null', renderArgs.model.searchResult
    	assertEquals 'View is incorrect', "list", renderArgs.view
    	assertEquals 'Wrong message was populated', 'tf.admin.search.toGeneral.message', controller.flash.message
    }
}
