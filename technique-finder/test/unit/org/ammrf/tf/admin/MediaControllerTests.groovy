package org.ammrf.tf.admin

import org.ammrf.tf.Media;
import org.ammrf.tf.MediaType;
import org.ammrf.tf.MediaFile;
import org.ammrf.tf.MediaFileService;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

import grails.test.*

class MediaControllerTests extends ControllerUnitTestCase {

    def media
    def mediaFile
    def mediaFileService
    def mfsContext
	
    protected void setUp() {
        super.setUp()
        
        MediaController.metaClass.message = { Map args -> return args['code']}
        MediaController.metaClass.redirect = { Map args -> return args}

        media = []
        mediaFile = []
        for(i in [2, 1, 3, 5, 4]) {
		def mediaFileOriginal = new MediaFile(location: 'images/'+(2*i), mime: 'image/png', width: 225, height: 180) 
		def mediaFileThumbnail = new MediaFile(location: 'images/'+(2*i+1), mime: 'image/png', width: 160, height: (180*160/225)) 
                mediaFile += mediaFileOriginal
                mediaFile += mediaFileThumbnail
        	def medium = new Media(name:'media' + i + '.png', caption:'caption for media ' + i, mediaType:MediaType.IMAGE, original:mediaFileOriginal, thumbnail:mediaFileThumbnail)
		media += medium
        }

        // mockDomain(MediaFile, mediaFile)
        mockDomain(Media, media)
        mediaFileService = new Expando()
        mfsContext = new Expando()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUpdate() {
    	def obj = media.get(0)
    	def file1 = newExpando(111, 'file1')
        def file2 = newExpando(222, 'file2')
        def file3 = newExpando(333, 'file3')
    	mediaFileService.createStore = { ctx -> return mfsContext }
    	mediaFileService.getMediaFile = { store, name ->
    	  return (name.startsWith('.THUM_') ? file2 : file1) 
    	  }
    	mediaFileService.removeMediaFile = { store, name -> 
    	  assertTrue 'requesting wrong file to remove', name ==  obj.original.location || name == obj.thumbnail.location 
    	  }
    	mediaFileService.createJpegThumbnail = { store, inputStream, file, boxsize ->
    	  assertTrue 'create file into temporary, was ' + file, file == file3
    	}
    	mediaFileService.getDimensions = { file, mediaType ->
    	  return (!file.toString().equals('file4') ? [width:111, height:222] : [width:444, height:555]) 
    	}
        mediaFileService.sanitizeFilename = { store, name ->
          assertTrue name.endsWith("test.jpg")
          return name
        }
        mediaFileService.createTemporary = {
          return file3
        }
        mediaFileService.moveToStore = { store, file, name ->
          assertEquals 'not the store', store, mfsContext
          assertEquals 'not the temporary. was ' + file, file3, file
        }
        mediaFileService.openInputStream = { file ->
          assertEquals 'is not temporary. was ' + file, file3, file
          return newExpando(444, 'file4')
        }
        controller.mediaFileService = mediaFileService

        byte[] bytes = [1]
        MockMultipartFile mp = new MockMultipartFile("newFile", "test.jpg", "multipart/form-data", bytes)
    	MockMultipartFile.metaClass.transferTo { file -> 
    	  assertTrue file == file1 
    	}
        controller.request.metaClass.mixin(MockMultipartHttpServletRequest)
        controller.request.addFile(mp)
        mockParams.id = media.get(0).id

        controller.update()
        assertEquals 111, media.get(0).original.width
        assertEquals 222, media.get(0).original.height
        assertEquals 444, media.get(0).thumbnail.width
        assertEquals 555, media.get(0).thumbnail.height

    }

    void testSave() {
    	def file1 = new Expando()
        file1.size = { return 111 }
        def file2 = new Expando()
        file2.size = { return 222 }
        def file3 = new Expando()
        file3.size = { return 333 }
    	mediaFileService.createStore = { ctx -> return mfsContext }
    	mediaFileService.getMediaFile = { store, name ->
    	  return (name.startsWith('.THUM_') ? file2 : file1) 
    	  }
    	mediaFileService.removeMediaFile = { store, name -> 
    	  assertFail 'requesting wrong file to remove'
    	  }
    	mediaFileService.createJpegThumbnail = { store, inputStream, file, boxsize ->
    	  assertTrue 'create file into temporary. was ' + file, file == file3
    	  }
    	mediaFileService.getDimensions = { file, mediaType ->
    	  return (file == file1 ? [width:111, height:222] : [width:444, height:555]) 
    	}
        mediaFileService.sanitizeFilename = { store, name ->
          assertTrue name.endsWith("test.jpg")
          return name
        }
        mediaFileService.createTemporary = {
          return file3
        }
        mediaFileService.openInputStream = { file ->
          assertTrue 'not temporary', file == file3
        }
        mediaFileService.moveToStore = { store, file, name ->
          assertEquals 'not the store', store, mfsContext
          assertEquals 'not the temporary. was ' + file, file3, file
        }
        controller.mediaFileService = mediaFileService

        byte[] bytes = [1]
        MockMultipartFile mp = new MockMultipartFile("newFile", "test.jpg", "multipart/form-data", bytes)
    	MockMultipartFile.metaClass.transferTo { file -> 
    	  assertTrue file == file1 
    	  }
        controller.request.metaClass.mixin(MockMultipartHttpServletRequest)
        controller.request.addFile(mp)
        mockParams.caption = 'saved_media'
        mockParams.mediaType = 'IMAGE'

        def redir = controller.save()
        
        assertEquals 'Wrong action', 'show', redir.action
        assertNotNull 'Id not there', redir.id
        def newInstance = Media.get(redir.id.toInteger())
        assertNotNull 'not saved', newInstance
	assertEquals 'Media not saved', 'saved_media', newInstance.caption
	assertNotNull 'image not saved', newInstance.original
	assertNotNull 'thumbnail not saved', newInstance.thumbnail
    }

    void testDelete() {
    	def obj = media.get(0)
        def objId = obj.id
    	mediaFileService.createStore = { ctx -> return mfsContext }
    	mediaFileService.removeMediaFile = { store, name -> 
          assertEquals 'wrong store', store, mfsContext
    	  assertTrue 'requesting wrong file to remove', name ==  obj.original.location || name == obj.thumbnail.location
    	  }
        controller.mediaFileService = mediaFileService
        mockParams.id = objId

        def redir = controller.delete()
	assertEquals 'wrong redirect', 'list', redir.action
        assertNull 'not deleted', Media.get(objId)
    }

    private newExpando(size, name) {
    	def file1 = new Expando()
        file1.size = { return size }
        file1.toString = { return name }
        return file1
    }

}
