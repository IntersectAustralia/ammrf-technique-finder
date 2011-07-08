package org.ammrf.tf.admin

import java.util.Map;
import org.ammrf.tf.Technique;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import org.ammrf.tf.*;
import grails.test.*

class TechniqueAdminControllerTests extends ControllerUnitTestCase {
	def techniques = []
	def contacts = []
	def location
	def techniqueService
	def mediaMap = [:]
	def emptyMediaMap = [:]
	def mediaIdsMap = [:]
	def emptyMediaIdsMap = [:]
	def media 
	
    protected void setUp() {
        super.setUp()
        
        for(i in [3,1,2]) {
        	techniques += new Technique(name:"Technique $i", summary:"The summary $i", description:"The description $i")
        }
        mockDomain(Technique, techniques)
        location = new Location(priority: 1, institution: 'Some test location', centerName: 'Some test center name', address: 'The address', state: State.NSW, status:LocationStatus.LL)
        mockDomain(Location, [location])
        
        for(i in 1..2) {
        	contacts += new Contact(title:"Test contact $i", name:"Some test name for a contact $i", position:'test', email:'test@ammrf.org.au', techniqueContact:true, location:location, telephone:'')
        }
        mockDomain(Contact, contacts)
        Contact.metaClass.'static'.findAll = { String query -> contacts }
        
        media = new Media(
				 	caption:'test', name:'test', mediaType:MediaType.IMAGE, 
				 	original:new MediaFile(location:'', mime:'', width:0, height:0, size:0), 
				 	thumbnail:new MediaFile(location:'', mime:'', width:0, height:0, size:0)
				 )
        mockDomain(Media, [media])
        
        mockDomain(MediaInSection)
        MediaSection.values().each { section ->
    	     mediaMap[section] = new MediaInSection(
    	    		 technique:techniques[0], section:section, priority:1, 
    	    		 media:media
    	     ).save(flush:true)
    	     emptyMediaMap[section] = []
    	     mediaIdsMap[section] = [media.id]
    	     emptyMediaIdsMap[section] = []
    	}
        
        techniqueService = mockFor(TechniqueService)
        TechniqueAdminController.metaClass.message = { Map args -> return args['code']}
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndexRedirect() {
    	mockParams.test = 'test'
    	controller.index()
    	assertEquals 'Wrong redirection', 'list', redirectArgs.action
    	assertEquals 'Wrong redirection params', mockParams, redirectArgs.params
    }
    
    void testList() {
    	def model = controller.list()
    	assertEquals 'Wrong list', techniques.sort {it.name}, model.instanceList
    }
    
    void testCreate() {
    	
	mockGetOptionsForAssociates()
	controller.techniqueService = techniqueService.createMock()
    	def model = controller.create()
    	assertEquals 'Wrong view', 'edit', renderArgs.view
    	assertNotNull 'Technique instance should not be null', model.instance
    	assertEquals 'Wrong contacts list', contacts, model.contactsForTechniques
    	assertEquals 'Wrong media map', emptyMediaMap, model.mediaMap
    }
    
    void testShow() {
    	mockGetMediaInTechniqueSection()
	
    	mockParams.id = techniques[0].id
    	def model = controller.show()
    	assertEquals 'Wrong technique', techniques[0], model.instance
    	assertEquals 'Wrong media map', mediaMap, model.mediaMap
    }
    
    void testShowNotFound() {
    	mockParams.id = 999
    	controller.show()
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    }
    
    void testEdit() {
    	mockGetMediaInTechniqueSection()
    	mockParams.id = techniques[0].id
	mockGetOptionsForAssociates()
    	def model = controller.edit()
    	
    	assertEquals 'Wrong technique', techniques[0], model.instance
    	assertEquals 'Wrong contacts list', contacts, model.contactsForTechniques
    	assertEquals 'Wrong media map', mediaMap, model.mediaMap
    }
    
    void testEditNotFound() {
    	mockParams.id = 999
    	controller.edit()
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    }
    
    void testListMedia() {
    	def mediaIdsCommand = new MediaIdsCommand(mediaSection:MediaSection.OUTPUT, mediaIds:[1])
    	techniqueService.demand.getMediaNotInTechniqueSection(){List<Long> mediaIds ->
    		return [media]
    	}
    	controller.techniqueService = techniqueService.createMock()
    	controller.listMedia(mediaIdsCommand)
    	
    	assertEquals 'Wrong template', 'mediaList', renderArgs.template
    	assertEquals 'Wrong media', [media], renderArgs.model.media
    	assertEquals 'Wrong section', mediaIdsCommand.mediaSection, renderArgs.model.section
    }
    
    void testListMediaEmpty() {
    	runTestListMediaError([])
    }
    
    void testListMediaNull() {
    	runTestListMediaError(null)
    }
    
    void runTestListMediaError(seviceReturnValue) {
    	def mediaIdsCommand = new MediaIdsCommand(mediaSection:MediaSection.OUTPUT, mediaIds:[1])
    	techniqueService.demand.getMediaNotInTechniqueSection(){List<Long> mediaIds ->
    		return seviceReturnValue
    	}
    	controller.techniqueService = techniqueService.createMock()
    	controller.listMedia(mediaIdsCommand)
    	
    	assertEquals 'Wrong response contentType', 'text/plain', mockResponse.contentType
    	assertEquals 'Wrong response content', 'EMPTY', mockResponse.contentAsString
    }
    
    void testSave() {
    	mockParams.name = "New name"
    	MediaSection.values().each {
    		mockParams."media_${it}_Ids" = "$media.id"
    	}
	mockGetOptionsForAssociates()
    	mockSaveTechnique(null, mediaIdsMap, true)
    	controller.techniqueService = techniqueService.createMock()
    	controller.save()
    	
    	assertEquals 'Wrong message was populated', 'default.created.message', controller.flash.message
    	assertEquals 'Wrong redirect', 'show', redirectArgs.action
    	assertNotNull 'Id should not be null', redirectArgs.id
    }
    
    void testSaveErrors() {
    	mockParams.name = "New name"
	mockGetOptionsForAssociates()
    	mockSaveTechnique(null, emptyMediaIdsMap, false)
    	mockGetMediaFromMediaMap(emptyMediaMap)
    	controller.techniqueService = techniqueService.createMock()
    	controller.save()
    	
    	assertEquals 'Wrong view', 'edit', renderArgs.view
    	assertNotNull 'Technique instance should not be null', renderArgs.model.instance
    	assertEquals 'Wrong contacts list', contacts, renderArgs.model.contactsForTechniques
    	assertEquals 'Wrong media map', emptyMediaMap, renderArgs.model.mediaMap
    }
    
    void testUpdate() {
    	mockParams.name = "New name"
    	mockParams.id = techniques[0].id
	mockGetOptionsForAssociates()
    	mockSaveTechnique(techniques[0], emptyMediaIdsMap, true)
    	controller.techniqueService = techniqueService.createMock()
    	controller.update()
    	
    	assertEquals 'Wrong message was populated', 'default.updated.message', controller.flash.message
    	assertEquals 'Wrong redirect', 'show', redirectArgs.action
    	assertEquals 'Wrong id', techniques[0].id, redirectArgs.id
    }
    
    void testUpdateErrors() {
    	mockParams.name = "New name"
    	mockParams.id = techniques[0].id
	mockGetOptionsForAssociates()
    	mockSaveTechnique(techniques[0], emptyMediaIdsMap, false)
    	mockGetMediaFromMediaMap(mediaMap)
    	controller.techniqueService = techniqueService.createMock()
    	controller.update()
    	
    	assertEquals 'Wrong view', 'edit', renderArgs.view
    	assertEquals 'Wrong technique instance', techniques[0], renderArgs.model.instance
    	assertEquals 'Wrong contacts list', contacts, renderArgs.model.contactsForTechniques
    	assertEquals 'Wrong media map', mediaMap, renderArgs.model.mediaMap
    }
    
    void testUpdateNotFound() {
    	mockParams.id = 999
    	controller.update()
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    }
    
    void testUpdateOptimisticException() {
    	def technique = techniques[0]
    	technique.version = 2
	mockGetOptionsForAssociates()
    	mockGetMediaFromMediaMap(mediaMap)
    	controller.techniqueService = techniqueService.createMock()
    	technique.save(flush:true)
    	mockParams.id = technique.id
    	mockParams.version = 1
    	
    	controller.update()
    	assertEquals 'Wrong view', 'edit', renderArgs.view
    	assertEquals 'Wrong message was populated', 'default.optimistic.locking.failure', technique.errors.version
    	assertEquals 'Wrong technique name in the model', technique.name, renderArgs.model.instance.name
    	assertEquals 'Wrong contacts list', contacts, renderArgs.model.contactsForTechniques
    	assertEquals 'Wrong media map', mediaMap, renderArgs.model.mediaMap
    }
    
    void testDelete() {
    	def technique = techniques[0]
		mockParams.id = technique.id
		
		controller.delete()
		assertEquals 'Wrong redirect', 'list', redirectArgs.action
		assertEquals 'Wrong message was populated', 'default.deleted.message', controller.flash.message
		assertNull 'Technique should have been deleted', Technique.get(technique.id)
    }
    
    void testDeleteNonExistingTechnique() {
    	mockParams.id = 999
    	
    	controller.delete()
    	assertEquals 'Wrong redirect', 'list', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.not.found.message', controller.flash.message
    }
    
    void testDeleteDataIntegrityViolationException() {
    	def technique = techniques[0]
    	Technique.metaClass.delete = { Map args = [:] -> throw new DataIntegrityViolationException("Error")}
    	mockParams.id = technique.id
    	
    	controller.delete()
    	assertEquals 'Wrong redirect', 'show', redirectArgs.action
    	assertEquals 'Wrong message was populated', 'default.not.deleted.message', controller.flash.message
    }
    
    private void mockGetMediaInTechniqueSection() {
    	MediaSection.values().each {
	    	techniqueService.demand.getMediaInTechniqueSection(){ Technique technique, MediaSection mediaSection ->
				mediaMap[mediaSection]
			}
    	}
		controller.techniqueService = techniqueService.createMock()
    }
    
    private void mockSaveTechnique(Technique technique, Map mediaMap, boolean returnValue) {
    	techniqueService.demand.saveTechnique(){ Technique _technique, Map _mediaMap ->
    		if(_technique?.id.equals(technique?.id) && _mediaMap.equals(mediaMap)) {
    			if(technique) {
    				_technique.properties = technique.properties
    			}
    			if(!_technique.id) {
    				_technique.id = 123
    			}
    			return returnValue
    		} else {
    			failNotEquals 'Wrong parameters where passed to <TechniqueService.saveTechnique> method', [technique?.id, mediaMap], [_technique.id, _mediaMap]
    		}
		}
    }

    private void mockGetOptionsForAssociates() {
	techniqueService.demand.getOptionsForAssociates(){return [:]}
    }

    
    private void mockGetMediaFromMediaMap(Map returnValue) {
    	techniqueService.demand.getMediaFromMediaMap(){ Map mediaMap ->
			return returnValue
		}
    }
}
