package org.ammrf.tf

import grails.test.*

/** test has to have two properties: mediaFileService and store */
class FileUtil {

    public static Map copyToStore(GrailsUnitTestCase test, name, storeName, mediaType) {
        def fis = test.getClass().getResource(name).openStream()
        File dest = test.mediaFileService.getMediaFile(test.store, storeName)
        dest.withOutputStream { fos -> 
            fis.eachByte { b ->
               fos.write(b)
            }
        }
        test.assertTrue 'File is not stored', dest.exists()
	return test.mediaFileService.getDimensions(dest, mediaType)
    }

    public static void removeFromStore(GrailsUnitTestCase test, storeName) {
        test.mediaFileService.removeMediaFile(test.store, storeName)
    }


}
