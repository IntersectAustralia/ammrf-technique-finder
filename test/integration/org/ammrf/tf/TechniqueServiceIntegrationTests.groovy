package org.ammrf.tf

import grails.test.*

class TechniqueServiceIntegrationTests extends GrailsUnitTestCase {
    def service
    def bioLeftOption
    def bioRightOption
    
    def physicsLeftOption
    def physicsRightOption
    
    def techniques = []
    def unAssociatedTechniques = []
       
    def techniquesAllFields = []
                                  
    def contact
    def location
    def review
    def caseStudy
    
    protected void setUp() {
        super.setUp()
        
        bioLeftOption = new Option(name:'Test bio option left', science:Science.BIOLOGY, type:OptionType.LEFT, priority:1).save()
        bioRightOption = new Option(name:'Test bio option right', science:Science.BIOLOGY, type:OptionType.RIGHT, priority:1).save()
        physicsLeftOption = new Option(name:'Test physics option left', science:Science.PHYSICS, type:OptionType.LEFT, priority:1).save()
        physicsRightOption = new Option(name:'Test physics option right', science:Science.PHYSICS, type:OptionType.RIGHT, priority:1).save()
        
        location = new Location(priority: 1, institution: 'Some test location', centerName: 'Some test center name', address: 'The address', state: State.NSW, status:LocationStatus.LL).save()
    	contact = new Contact(title:'Test contact', name:'Some test name for a contact', position:'test', email:'test@ammrf.org.au', techniqueContact:true, location:location, telephone:'').save()
    	review = new Review(referenceNames:'Some Names', title:'Test review', fullReference:'reference', url:'http://www.ammrf.org.au').save()
    	caseStudy = new CaseStudy(name:'Test case study', url:'http://www.ammrf.org.au').save()
        
        def technique = new Technique(name:'Technique 1', summary:'The summary', description:'The description', keywords:'', alternativeNames:'', contacts:[contact], reviews:[review], caseStudies:[caseStudy])
        technique.save()
        
        new OptionCombination(left:bioLeftOption, right:bioRightOption, priority:1, technique:technique).save()
        new OptionCombination(left:physicsLeftOption, right:physicsRightOption, priority:2, technique:technique).save()
        techniques.add([id:technique.id, name:technique.name, summary:technique.summary])
        techniquesAllFields.add(technique)
        
        technique = new Technique(name:'Technique 2', summary:'The summary', description:'The description', keywords:'', alternativeNames:'', contacts:[contact], reviews:[review], caseStudies:[caseStudy])
        technique.save()
        
        new OptionCombination(left:bioLeftOption, right:bioRightOption, priority:2, technique:technique).save()
        new OptionCombination(left:physicsLeftOption, right:physicsRightOption, priority:1, technique:technique).save()
        techniques.add([id:technique.id, name:technique.name, summary:technique.summary])
        techniquesAllFields.add(technique)

        technique = new Technique(name:'Technique 3', summary:'The summary', description:'The description', keywords:'', alternativeNames:'')
        technique.save()
        unAssociatedTechniques.add([id:technique.id, name:technique.name, summary:technique.summary])
        
        service = new TechniqueService()
    }
    
    protected void tearDown() {
        super.tearDown()
		OptionCombination.executeUpdate("delete from OptionCombination")
		
		def techniques = Technique.findAll()
		techniques.each {
			if(it.contacts) it.contacts.clear()
			if(it.reviews) it.reviews.clear()
			if(it.caseStudies) it.caseStudies.clear()
		}
		
		Technique.executeUpdate("delete from Technique")
		CaseStudy.executeUpdate("delete from CaseStudy")
		Review.executeUpdate("delete from Review")
		Contact.executeUpdate("delete from Contact")
		Location.executeUpdate("delete from Location")
		Option.executeUpdate("delete from Option")
    }
    
    void testFindTechniquesByBioOptionCombination() {
        def foundTechniques = service.findTechniquesByOptionCombination(bioLeftOption, bioRightOption, ['id', 'name', 'summary'])
        assertEquals 'Techniques were not found' , [techniques[0], techniques[1]], foundTechniques
    }
    
    void testFindTechniquesByPhysicsOptionCombination() {
        def foundTechniques = service.findTechniquesByOptionCombination(physicsLeftOption, physicsRightOption, ['id', 'name', 'summary'])
        assertEquals 'Techniques were not found' , [techniques[1], techniques[0]], foundTechniques
    }
	
	void testFindAllTechniques() {
		def foundTechniques = service.findAllTechniques(['id', 'name'])
		def expectedTechniques = techniques.plus(unAssociatedTechniques).collect {[id:it.id, name:it.name]}
		assertEquals 'Techniques were not found' , expectedTechniques.sort{it.name}, foundTechniques
	}
    
    void testGetTechniquesNotInList() {
    	def foundTechniques = service.getTechniquesNotInList(techniques.collect{it.id})
        assertEquals 'Techniques were not found' , unAssociatedTechniques.collect{[id:it.id, name:it.name]}, foundTechniques
    }
    
    void testGetTechniquesNotInListNullList() {
    	def foundTechniques = service.getTechniquesNotInList(null)
        assertEquals 'All techniques should have been returned' , (techniques + unAssociatedTechniques).collect{[id:it.id, name:it.name]}.sort{it.name}, foundTechniques
    }
    
    void testGetTechniquesNotInListEmptyList() {
    	def foundTechniques = service.getTechniquesNotInList([])
        assertEquals 'All techniques should have been returned' , (techniques + unAssociatedTechniques).collect{[id:it.id, name:it.name]}.sort{it.name}, foundTechniques
    }
    
    void testUpdateTechniquesAssociationWithOptionCombination() {
    	service.updateTechniquesAssociationWithOptionCombination([techniques[1].id, unAssociatedTechniques[0].id], bioLeftOption, bioRightOption)
    	def foundTechniques = service.findTechniquesByOptionCombination(bioLeftOption, bioRightOption, ['id', 'name', 'summary'])
    	assertEquals 'Wrong techniques were found' , [techniques[1], unAssociatedTechniques[0]], foundTechniques
    }
    
    void testUpdateTechniquesAssociationWithOptionCombinationNullIdList() {
    	service.updateTechniquesAssociationWithOptionCombination(null, bioLeftOption, bioRightOption)
    	def foundTechniques = service.findTechniquesByOptionCombination(bioLeftOption, bioRightOption, ['id', 'name', 'summary'])
    	assertEquals 'Wrong techniques were found' , [], foundTechniques
    }
    
    void testUpdateTechniquesAssociationWithOptionCombinationEmptyIdList() {
    	service.updateTechniquesAssociationWithOptionCombination([], bioLeftOption, bioRightOption)
    	def foundTechniques = service.findTechniquesByOptionCombination(bioLeftOption, bioRightOption, ['id', 'name', 'summary'])
    	assertEquals 'Wrong techniques were found' , [], foundTechniques
    }
    
    void testFindTechniquesByContact() {
    	checkFindTechniquesByRelation(contact)
    }
    
    void testFindTechniquesByReview() {
    	checkFindTechniquesByRelation(review)
    }
    
    void testFindTechniquesByCaseStudy() {
    	checkFindTechniquesByRelation(caseStudy)
    }
    
    void testFindTechniquesByLocation() {
    	checkFindTechniquesByRelation(location)
    }
	
	void testFindTechniquesByOption() {
		checkFindTechniquesByRelation(bioLeftOption)
	}
    
    void testFindTechniquesByUnsupportedObject() {
    	shouldFail(IllegalArgumentException) {
    		service.findTechniquesByRelation(new StaticContent(name: 'Some test name', text: 'Some test text'))
    	}
    }
    
    private void checkFindTechniquesByRelation(object) {
    	def foundTechniques = service.findTechniquesByRelation(object)
    	assertEquals 'Wrong techniques found', techniquesAllFields, foundTechniques
    }
    
}
