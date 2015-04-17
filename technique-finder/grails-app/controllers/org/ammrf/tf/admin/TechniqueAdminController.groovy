package org.ammrf.tf.admin

import org.ammrf.tf.Technique;
import org.ammrf.tf.Contact;
import org.ammrf.tf.MediaSection;
import org.ammrf.tf.TechniqueService;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

import java.util.List;


class MediaIdsCommand {
        MediaSection mediaSection
	List<Long> mediaIds
}

class ListIds {
	String type
	List<Long> ids
}

@Secured(['ROLE_ADMIN'])
class TechniqueAdminController {

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"], listMedia: "POST", saveMedia: "POST", listAssociates: ["POST", "GET"]]

    def techniqueService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        [instanceList: Technique.list(sort:'name')]
    }

    def create = {
        def techniqueInstance = new Technique()
        techniqueInstance.properties = params
	def mediaMap = [:] as Map
	[MediaSection.OUTPUT,MediaSection.INSTRUMENT,MediaSection.LIST].each { section ->
	     mediaMap[section] = []
	}
	def associatesOptions = techniqueService.getOptionsForAssociates()
	render(view:"edit",  model:[instance: techniqueInstance, contactsForTechniques:contactsForTechniques(), mediaMap:mediaMap, associatesOptions:associatesOptions])
    }

    def show = {
        def techniqueInstance = Technique.get(params.id)
        if (!techniqueInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technique.label', default: 'Technique'), params.id])}"
            redirect(action: "list")
        }
        else {
            def mediaMap = [:] as Map
	    MediaSection.values().each { section ->
		mediaMap[section] = techniqueService.getMediaInTechniqueSection(techniqueInstance, section)
            }
            [instance: techniqueInstance, mediaMap:mediaMap]
        }
    }

    def edit = {
        def techniqueInstance = Technique.get(params.id)
        if (!techniqueInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technique.label', default: 'Technique'), params.id])}"
            redirect(action: "list")
        }
        else {
            def mediaMap = [:] as Map
	    MediaSection.values().each { section ->
		mediaMap[section] = techniqueService.getMediaInTechniqueSection(techniqueInstance, section)
            }
	    def associatesOptions = techniqueService.getOptionsForAssociates()
            [instance: techniqueInstance, contactsForTechniques:contactsForTechniques(), mediaMap:mediaMap, associatesOptions:associatesOptions]
        }
    }

    def listMedia = {MediaIdsCommand cmd ->
        def media = techniqueService.getMediaNotInTechniqueSection(cmd.mediaIds)
        listDo(media, 'mediaList', 'media', [section: cmd.mediaSection])
    }

    def listAssociates = {ListIds cmd ->
        def associates = techniqueService.getAssociatesNotInTechnique(cmd?.type, cmd?.ids)
        listDo(associates, 'associatesList', 'associates', techniqueService.getOptionsForAssociateType(cmd.type).plus(type:cmd?.type))
    }

    private listDo(List objs, String template, String modelTag, Map defaults) {
        if (objs == null || objs.size() == 0) {
            response.contentType = "text/plain"
            render "EMPTY"
            return
        } else {
            defaults[modelTag] = objs
            render(template:template, model:defaults)
        }
    }

    def save = {
        def techniqueInstance = new Technique(params)
        def mediaMap = [:] as Map
        MediaSection.values().each { section ->
	    mediaMap[section] = listify(params."media_${section}_Ids")
        }
	def associatesOptions = techniqueService.getOptionsForAssociates()
        if (techniqueService.saveTechnique(techniqueInstance, mediaMap)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'technique.label', default: 'Technique'), techniqueInstance.id])}"
            redirect(action: "show", id: techniqueInstance.id)
        }
        else {
            render(view: "edit", model: [instance: techniqueInstance, contactsForTechniques:contactsForTechniques(),
		mediaMap:grabMedia(mediaMap), associatesOptions:associatesOptions])
        }
    }

    def update = {
        def techniqueInstance = Technique.get(params.id)
        if (techniqueInstance) {
        	def mediaMap = [:] as Map
            MediaSection.values().each { section ->
            	mediaMap[section] = listify(params."media_${section}_Ids")
            }
	    def associatesOptions = techniqueService.getOptionsForAssociates()
            if (params.version) {
                def version = params.version.toLong()
                if (techniqueInstance.version > version) {
                    
                    techniqueInstance.errors.rejectValue("version", "default.optimistic.locking.failure", 
                         [message(code: 'technique.label', default: 'Technique')] as Object[],
                         "Another user has updated this Technique while you were editing")
                    render(view: "edit", model: [instance: techniqueInstance, contactsForTechniques:contactsForTechniques(), mediaMap:grabMedia(mediaMap)])
                    return
                }
            }
            techniqueInstance.contacts = null
            techniqueInstance.reviews = null
            techniqueInstance.caseStudies = null
            techniqueInstance.properties = params

            if (techniqueService.saveTechnique(techniqueInstance, mediaMap)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'technique.label', default: 'Technique'), techniqueInstance.id])}"
                redirect(action: "show", id: techniqueInstance.id)
            }
            else {
                render(view: "edit", model: [instance: techniqueInstance, contactsForTechniques:contactsForTechniques(),
			mediaMap:grabMedia(mediaMap), associatesOptions:associatesOptions])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technique.label', default: 'Technique'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def techniqueInstance = Technique.get(params.id)
        if (techniqueInstance) {
            try {
                techniqueInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'technique.label', default: 'Technique'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'technique.label', default: 'Technique'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technique.label', default: 'Technique'), params.id])}"
            redirect(action: "list")
        }
    }

    private Collection contactsForTechniques() {
         return Contact.findAll('from Contact as c where c.techniqueContact=true order by c.location.priority, c.name')
    }

    private def grabMedia(mediaMap) {
         return techniqueService.getMediaFromMediaMap(mediaMap)
    }

    private List<Long> listify(String var) {
         if (var) {
             return var.split(',').collect { it.toLong() }
         } else {
             return [] as List<Long>
         }
    }

}
