package org.ammrf.tf.admin

import org.ammrf.tf.Media;
import org.ammrf.tf.MediaFile;
import org.ammrf.tf.MediaType;

import grails.test.*

class MediaControllerIntegrationTests extends ControllerUnitTestCase {

    def controller
    def media
	
    protected void setUp() {
        super.setUp()
        media = []
        for(i in [2, 1, 3, 5, 4]) {
		def mediaFileOriginal = new MediaFile(location: 'images/'+(2*i), mime: 'image/png', width: 225, height: 180, size:100) 
		def mediaFileThumbnail = new MediaFile(location: 'images/'+(2*i+1), mime: 'image/png', width: 160, height: (180*160/225), size:100) 
        	def medium = new Media(name:'media' + i + '.png', caption:'caption for media ' + i, mediaType:MediaType.IMAGE, original:mediaFileOriginal, thumbnail:mediaFileThumbnail)
		medium.save(flush:true)
		media += medium
        }
        MediaController.metaClass.message = { Map args -> return args['code']}
	controller = new MediaController()
    }

    protected void tearDown() {
        super.tearDown()
		Media.executeUpdate("delete from Media")
		MediaFile.executeUpdate("delete from MediaFile")
    }

    void testListSorting() {
        def mediaSorted = media.sort { a, b -> a.name <=> b.name }
    	assertEquals 'List sorting is incorrect', mediaSorted, controller.list().instanceList
    }
    

    void testParamAndOffset() {
    	controller.params.max = 2
        controller.params.offset = 2
        assertEquals media.size(), controller.list().instanceList.size()
    }

    void testShowWrong() {
	controller.params.id = -1
        controller.show()
        assertEquals 'should be not found', 'default.not.found.message', controller.flash.message
        assertEquals 'redirect wrong', 'list', controller.redirectArgs.action
    }

    void testEditWrong() {
	controller.params.id = -1
        def resp = controller.edit()
        assertEquals 'should be not found', 'default.not.found.message', controller.flash.message
        assertEquals 'redirect wrong', 'list', controller.redirectArgs.action
    }

    void testEdit() {
	controller.params.id = media.get(0).id
        def model = controller.edit()
        assertEquals 'wrong object', media.get(0).id, model.instance.id
    }

    void testCreate() {
        controller.params.mediaType = 'IMAGE'
        controller.create()
        assertEquals 'view wrong', 'edit', controller.renderArgs.view
        assertNull 'Not a new instance', controller.renderArgs.model.instance.id
    }

}
