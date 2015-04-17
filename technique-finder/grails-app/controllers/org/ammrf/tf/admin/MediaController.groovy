package org.ammrf.tf.admin

import org.ammrf.tf.Media;
import org.ammrf.tf.MediaType;
import org.ammrf.tf.MediaFile;
import org.ammrf.tf.MediaInSection;
import org.ammrf.tf.MediaFileService;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

/**
 * Admin controller to list/view/edit/delete locations (AMMRF nodes)
 * 
 * @author Carlos Aya
 *
 */
@Secured(['ROLE_ADMIN'])
class MediaController {

    static defaultAction = "list"

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
    
    def mediaFileService
                           
    def list = {
        [instanceList: Media.list(sort:'name')]
    }

    def show = {
        def instance = Media.get(params.id)
        if (!instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
        else {
            def techniques = MediaInSection.executeQuery("select mis.technique from MediaInSection mis where mis.media.id = :id", [id:instance.id]) 
            [instance: instance, techniques:techniques]
        }
    }

    def edit = {
        def instance = Media.get(params.id)
        if (!instance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
        else {
            [instance: instance, mediaType:instance.mediaType]
        }
    }

    def create = {
	def instance = new Media(caption: '', original: null, thumbnail: null, mediaType: MediaType.valueOf(params.mediaType))
	render(view:"edit",  model:[instance: instance, mediaType:instance.mediaType])
    }

    def update = {
        def instance = Media.get(params.id)
        if (instance) {
            if (params.version) {
                def version = params.version.toLong()
                if (instance.version > version) {
                    
                    instance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'media.label', default: 'Media')] as Object[], "Another user has updated this media while you were editing")
                    render(view: "edit", model: [instance: instance, mediaType:instance.mediaType])
                    return
                }
            }
            // update instance
	    def context = getServletContext()
	    def newFile = request.getFile('newFile')
	    def thumbnailFile = instance.mediaType == MediaType.MOVIE ? request.getFile('thumbnailFile') : null

	    instance.caption = params.caption

            def actions = updateInstance(context, instance, newFile, thumbnailFile)

            // validate
            if (!instance.hasErrors() && instance.save(flush: true)) {
                doFileHandling(actions)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'media.label', default: 'Media'), instance.id])}"
                redirect(action: "show", id: instance.id)
            }
            else {
                render(view: "edit", model: [instance: instance, mediaType:instance.mediaType])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
    }

    def save = {
	def instance = new Media(caption: '', original: null, thumbnail: null, mediaType: MediaType.valueOf(params.mediaType))
	instance.caption = params.caption
	def context = getServletContext()
	def newFile = request.getFile('newFile')
	def thumbnailFile = instance.mediaType == MediaType.MOVIE ? request.getFile('thumbnailFile') : null

        def actions = updateInstance(context, instance, newFile, thumbnailFile)

        // validate
        if (!instance.hasErrors() && instance.save(flush: true)) {
            doFileHandling(actions)
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'media.label', default: 'Media'), instance.id])}"
            redirect(action: "show", id: instance.id)
        }
        else {
            render(view: "edit", model: [instance: instance, mediaType:instance.mediaType])
        }
   }

   def delete = {
        def instance = Media.get(params.id)
	def context = getServletContext()
        if (instance) {
            try {

                instance.delete(flush: true)

                def store = mediaFileService.createStore(context)

		// delete objects from file store
		if (instance.original) {
			mediaFileService.removeMediaFile(store, instance.original?.location)
		}
		if (instance.thumbnail) {
			mediaFileService.removeMediaFile(store, instance.thumbnail?.location)
		}

                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                redirect(action: "show", id: params.id)
            }

        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
   }

   private def updateInstance(context, instance, newFile, thumbnailFile) {
        def actions = []
        def store = mediaFileService.createStore(context)

	instance.caption = params.caption
	if (newFile != null && !newFile.empty) {
		if (instance.original) {
			def oldLocation = instance.original.location
			actions += {mediaFileService.removeMediaFile(store, oldLocation)}
		}
                def newLocation = sanitizeFilename(store, newFile.originalFilename)
		instance.name = newLocation
	        def origData = mediaFileService.getDimensions( newFile.getInputStream(), instance.mediaType )
	        instance.original = new MediaFile(mime: newFile.contentType, size:newFile.getSize(), 
					    width:origData.width, height: origData.height, location: newLocation)
		def fileStored = mediaFileService.getMediaFile(store, newLocation)
		actions += {newFile.transferTo( fileStored )}
        }
 
        if (instance.mediaType == MediaType.IMAGE) {
		if (newFile != null && !newFile.empty) {
			if (instance.thumbnail) {
				def oldThumlocation = instance.thumbnail.location
				actions += {mediaFileService.removeMediaFile(store, oldThumlocation)}
			}
        		def thumbnailLocation = sanitizeFilename(store, '.THUM_' + instance.name);
                        // create image in temporary location
                        instance.thumbnail = new MediaFile(mime: 'image/jpeg', size:0, width:0, height: 0, location: thumbnailLocation)
                        def thumbStored = mediaFileService.createTemporary()
		        mediaFileService.createJpegThumbnail(store, newFile.getInputStream(), thumbStored, 100)
		        def thumbData = mediaFileService.getDimensions(mediaFileService.openInputStream(thumbStored), instance.mediaType )
		        instance.thumbnail.width = thumbData.width
		        instance.thumbnail.height = thumbData.height
		        instance.thumbnail.size = thumbStored.size()
                        // move to store
                        actions += {mediaFileService.moveToStore(store, thumbStored, thumbnailLocation)}
                }
        } else {
		if (thumbnailFile != null && !thumbnailFile.empty) {
			if (instance.thumbnail) {
				def oldThumlocation = instance.thumbnail.location
				actions += {mediaFileService.removeMediaFile(store, oldThumlocation)}
			}

                        def newThumbnailLocation = sanitizeFilename(store, thumbnailFile.originalFilename)
        		instance.thumbnail = new MediaFile(mime: thumbnailFile.contentType, size:0, width:0, height: 0, location: newThumbnailLocation)
        		def thumbStored = mediaFileService.getMediaFile(store, newThumbnailLocation)
        		actions += {thumbnailFile.transferTo( thumbStored )}
			def thumbData = mediaFileService.getDimensions( thumbnailFile.getInputStream(), MediaType.IMAGE )
			instance.thumbnail.width = thumbData.width
			instance.thumbnail.height = thumbData.height
			instance.thumbnail.size = thumbnailFile.getSize()
                }
        }

        return actions
   }

   private def doFileHandling(actions) {

	actions.each { it -> it() }

   }

   private String sanitizeFilename(store, filename) {
        return mediaFileService.sanitizeFilename(store, filename)
   }

}
