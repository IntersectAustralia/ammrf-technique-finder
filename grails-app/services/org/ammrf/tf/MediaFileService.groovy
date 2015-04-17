package org.ammrf.tf

import org.ammrf.tf.Media;
import org.ammrf.tf.MediaFile;

import javax.imageio.ImageIO as IIO
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.geom.AffineTransform

class MediaFileService {

    def grailsApplication
    boolean transactional = false

    def getMediaFile(store, name) {
	File file = new File(store, name)
	return file
    }

    def getDimensions(inputStream, mediaType) {
        if (mediaType == MediaType.IMAGE) {
           try {
	      BufferedImage bsrc = IIO.read(inputStream)
	      int width = bsrc.getWidth()
              int height = bsrc.getHeight()
	      return [width: width, height: height]
           } catch (Exception e) {
              return [width: 0, height: 0]
           }
        } else {
	   return [width: 225, height: 169]
        }
    }

    def createStore(context) {
		String mediaDir = "${grailsApplication.config.tf.mediafolder.path}"
		log.debug "Using media store directory:$mediaDir"
        File contextRoot = new File(mediaDir)
        if (! contextRoot.exists()) {
            contextRoot.mkdirs()
        }
	return contextRoot
    }

    def createTemporary() {
        return File.createTempFile("TF_",".jpg")
    }

    def moveToStore(store, tempFile, name) {
        if (!tempFile.renameTo(new File(store, name))) {
            throw new Exception("Temporary file " + tempFile.getName() + " couldn't be moved to " + name);
        }
    }

    def createJpegThumbnail(store, inputStream, destFile, max_box) {
        try {
		BufferedImage bsrc = IIO.read(inputStream)
		int width = bsrc.getWidth()
		int height = bsrc.getHeight()
		int newWidth
		int newHeight
		if (width > max_box) {
		   newWidth = max_box
		   newHeight = (height * max_box) / width
		} else {
		   newWidth = width
		   newHeight = height
		}
		if (newHeight > max_box) {
		   newWidth = (newWidth * max_box) / newHeight
		   newHeight = max_box
		}
		BufferedImage bdest = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB)
		Graphics2D g = bdest.createGraphics()
		AffineTransform at = AffineTransform.getScaleInstance((double)newWidth/width,
		  (double)newHeight/height)
		g.drawRenderedImage(bsrc,at)
		IIO.write(bdest,"JPG", destFile)
        } catch (Exception e) {
                // swallow exception, will fail after call to getDimensions and then instance will be wrong.
        }
    }

    def removeMediaFile(store, location) {
	def file = new File(store, location)
	if (file.exists()) {
	    file.delete()
        }
    }

    def sanitizeFilename(store, filename) {
        filename = filename.replaceAll("[^A-Za-z0-9 ,._-]+","")
        def dotPos = filename.lastIndexOf(".")
	def ext = dotPos == -1 ? "" : filename.substring(dotPos)
        def name = dotPos == -1 ? filename : filename.substring(0, dotPos)
        if (name.length() == 0) {
            filename = "BADNAME" + ext
        }
        def file = new File(store, filename)
        if (file.exists()) {
            def i = 1
	    while (file.exists()) {
	    	filename = name + "_" + (i++) + ext
                file = new File(store, filename)
	    }
        }
        return filename
   }

   def openInputStream(file) {
        return new FileInputStream(file)
   }

}
