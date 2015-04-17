package org.ammrf.tf

import javax.servlet.ServletContext

import grails.test.*

class MediaFileServiceIntegrationTests extends GrailsUnitTestCase {

    def mediaFileService
    def store

    protected void setUp() {
        super.setUp()
        mediaFileService = new MediaFileService()
        mediaFileService.grailsApplication = [config:[tf:[mediafolder:[path:"media"]]]]
        def context = new Expando()
        context.getRealPath = { s ->
           def file = new File("target/test-reports/dummy-context" + s)
           file.mkdirs()
           return file.getAbsolutePath()
        }
        store = mediaFileService.createStore(context)
        FileUtil.copyToStore(this, 'image1.jpg', 'original', MediaType.IMAGE)
    }

    protected void tearDown() {
        FileUtil.removeFromStore(this, 'original')
        super.tearDown()
    }

    void testCreateJpegThumbnail() {
        def origFile = mediaFileService.getMediaFile(store, 'original')
        assertTrue 'image was not copied', origFile.exists()
        def tempFile = mediaFileService.createTemporary()
        mediaFileService.createJpegThumbnail(store, new FileInputStream(origFile), tempFile, 20)
        assertTrue 'file not created', tempFile.exists()
        def dims = mediaFileService.getDimensions(new FileInputStream(tempFile), MediaType.IMAGE)
        assertTrue 'image not properly scaled', dims.width <= 20 && dims.height <= 20
    }

    void testCreateTemporary() {
        assertEquals 'not fresh temporary file', 0, mediaFileService.createTemporary().size()
    }

    /* NEED TO PASS IN HUDSON
    void testMoveToStore() {
        def temp = mediaFileService.createTemporary()
        def content = 'abcb'.getBytes()
        temp.withOutputStream { fos -> 
            content.each { b ->
               fos.write(b)
            }
        }
        assertTrue 'temporary is created', temp.exists()
        mediaFileService.moveToStore(store, temp, 'nename')
        assertTrue 'File not moved', mediaFileService.getMediaFile(store, 'nename').exists()
    }
    */

}
