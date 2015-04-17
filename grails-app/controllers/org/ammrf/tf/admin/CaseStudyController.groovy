package org.ammrf.tf.admin

import org.ammrf.tf.CaseStudy;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

/**
 * Admin controller to list/edit/delete case studies
 * 
 * @author Andrey Chernyshov
 *
 */
@Secured(['ROLE_ADMIN'])
class CaseStudyController {

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
                             
    def defaultAction = "list"
    
    def list = {
        [caseStudyInstanceList: CaseStudy.list(sort:'name')]
    }

    def create = {
        def caseStudyInstance = new CaseStudy()
        caseStudyInstance.properties = params
        return [caseStudyInstance: caseStudyInstance]
    }

    def save = {
        def caseStudyInstance = new CaseStudy(params)
        if (caseStudyInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'caseStudy.label', default: 'CaseStudy'), caseStudyInstance.id])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [caseStudyInstance: caseStudyInstance])
        }
    }

    def edit = {
        def caseStudyInstance = CaseStudy.get(params.id)
        if (!caseStudyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseStudy.label', default: 'CaseStudy'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [caseStudyInstance: caseStudyInstance]
        }
    }

    def update = {
        def caseStudyInstance = CaseStudy.get(params.id)
        if (caseStudyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (caseStudyInstance.version > version) {
                    
                    caseStudyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'caseStudy.label', default: 'CaseStudy')] as Object[], "Another user has updated this CaseStudy while you were editing")
                    render(view: "edit", model: [caseStudyInstance: caseStudyInstance])
                    return
                }
            }
            caseStudyInstance.properties = params
            if (!caseStudyInstance.hasErrors() && caseStudyInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'caseStudy.label', default: 'CaseStudy'), caseStudyInstance.id])}"
                flash.updatedObjectId = caseStudyInstance.id
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [caseStudyInstance: caseStudyInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseStudy.label', default: 'CaseStudy'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def caseStudyInstance = CaseStudy.get(params.id)
        if (caseStudyInstance) {
            try {
                caseStudyInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'caseStudy.label', default: 'CaseStudy'), params.id])}"
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'caseStudy.label', default: 'CaseStudy'), params.id])}"
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'caseStudy.label', default: 'CaseStudy'), params.id])}"
        }
        redirect(action: "list")
    }
}
