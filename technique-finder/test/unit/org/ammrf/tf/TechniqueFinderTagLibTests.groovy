package org.ammrf.tf

import java.util.TreeSet;
import grails.test.*

class TechniqueFinderTagLibTests extends TagLibUnitTestCase {
	
	def review
	def techniques = []
	def techniqueService
	def staticContentService
	
    protected void setUp() {
        super.setUp()
        review = new Review(referenceNames:'Some Names', title:'Test review', fullReference:'reference', url:'www.test.com')
        mockForConstraintsTests(Review, [review])
        for(i in 3..1) {
        	techniques += new Technique(id:i, name: 'Some test name $i', summary: 'Some test text', description: 'The description', reviews:new TreeSet<Review>([review]))
        }
        mockForConstraintsTests(Technique, techniques)
        
        techniqueService = mockFor(TechniqueService, true)
        techniqueService.demand.findTechniquesByRelation() {
    		object -> techniques
    	}
        tagLib.techniqueService = techniqueService.createMock()
		
		staticContentService = mockFor(StaticContentService, true)
		staticContentService.demand.findText() {
			code -> return code
		}
        tagLib.staticContentService = staticContentService.createMock()
		
        tagLib.metaClass.createLink = {Map params -> return "/technique-finder/$params.controller/$params.action/$params.id"}
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListAssociatedTechiques() {
    	tagLib.listAssociatedTechiques(object:review) {}
    	def expectedString = "<ul>"
    	techniques.each {expectedString += "<li><a href='/technique-finder/techniqueAdmin/show/$it.id'>$it.name</a></li>"}
    	expectedString += "</ul>"
    	assertEquals 'Wrong list', expectedString, tagLib.out.toString()
    }
    
    void testNavigationLinksNextOnly() {
    	checkNavigationLinks(techniques[0], null, techniques[1].id)
    }
    
    void testNavigationLinksPreviousOnly() {
    	checkNavigationLinks(techniques[2], techniques[1].id, null)
    }
    
    void testNavigationLinksBoth() {
    	checkNavigationLinks(techniques[1], techniques[0].id, techniques[2].id)
    }
    
    void testNavigtionLinksListEmpty() {
    	checkNavigationLinks(new Technique(id:999, name: 'Some test name $i', summary: 'Some test text', description: 'The description'), null, null)
    }
    
    void testNavigationLinksForOptions() {
    	def options = []
        for(i in 3..1) {
        	options += new Option(priority:i, name:"Test option $i", science:Science.BIOLOGY, type:OptionType.RIGHT)
        }
    	mockForConstraintsTests(Option, options)
    	Option.metaClass.'static'.executeQuery = {String query, Map params -> return options.collect{ it.id }}
    	checkNavigationLinks(options[1], options[0].id, options[2].id, "option", "edit", "priority")
    }
	
	void testStaticContent() {
		def code = 'tf.menu'
		tagLib.staticContent(code:code){}
		assertEquals 'Wrong text', code, tagLib.out.toString()
	}
	
	void testStaticContentNoCodeAttribute() {
		tagLib.staticContent(test:'test'){}
		assertEquals 'Wrong text', '', tagLib.out.toString()
	}
    
    private void checkNavigationLinks(currentObject, prevObjectId, nextObjectId, controller = "techniqueAdmin", action = "show", sort = "name") {
    	Technique.metaClass.'static'.executeQuery = {String query -> return techniques.collect{ it.id }}
    	
    	tagLib.navigationLinks(controller:controller, action:action, currentObject:currentObject, sort:sort){}
    	def expectedOut = ""
    	if(prevObjectId) {
    		expectedOut += "<a href='/technique-finder/$controller/$action/$prevObjectId'>< previous</a>"
    	}
    	if(nextObjectId) {
    		if(prevObjectId) {
    			expectedOut += " | "
    		}
    		expectedOut += "<a href='/technique-finder/$controller/$action/$nextObjectId'>next ></a>"
    	}
    	assertEquals 'Wrong links', expectedOut, tagLib.out.toString()
    }
}
