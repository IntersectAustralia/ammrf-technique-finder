package org.ammrf.tf.admin

import java.util.List;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;
import org.ammrf.tf.*;

/**
 * Command class to hold technique Ids
 */
class TechniqueIdsCommand {
	List<Long> techniqueIds
}

/**
 * Admin controller to associate techniques with options
 * 
 * @author Andrey Chernyshov
 *
 */
@Secured(['ROLE_ADMIN'])
class OptionsAssociationController extends OptionsSelectionController {
	
	def techniqueService
	
	/**
	 * Lists associated techniques for the selected options combination
	 */
	def listAssociatedTechniques = {
		def leftOption = Option.get(params.leftOption)
		if(!leftOption) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.left.label', default: 'Left side option'), params.leftOption])}"
			render(template:"/templates/ajaxError")
			return
		}
		
		def rightOption = Option.get(params.rightOption)
		if(!rightOption) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.right.label', default: 'Right side option'), params.rightOption])}"
			render(template:"/templates/ajaxError")
			return
		}
		
		try {
			def techniques = techniqueService.findTechniquesByOptionCombination(leftOption, rightOption, ['id', 'name'])
			render(template:"associatedTechniques", model:[techniques:techniques])
		} catch (IllegalArgumentException ex) {
			log.error "Error in finding associated techniques", ex
			flash.message="${message(code: 'tf.ajax.error', args: ['IllegalArgumentException', ex.message])}"
			render(template:"/templates/ajaxError")
		}
	}
	
	/**
	 * Lists all available techniques except passed one
	 */
	def listTechniques = {TechniqueIdsCommand cmd ->
		log.debug "params:$params"
		def techniques = techniqueService.getTechniquesNotInList(cmd.techniqueIds)
		render(template:"techniqueList", model:[techniques:techniques])
	}
	
	/**
	 * Updates associations
	 */
	def update = {TechniqueIdsCommand cmd ->
		log.debug "params:$params"
		def leftOption = Option.get(params.leftOption)
		if(!leftOption) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.left.label', default: 'Left side option'), params.leftOption])}"
			render(template:"/templates/ajaxError")
			return
		}
		
		def rightOption = Option.get(params.rightOption)
		if(!rightOption) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.right.label', default: 'Right side option'), params.rightOption])}"
			render(template:"/templates/ajaxError")
			return
		}
		
		try {
			techniqueService.updateTechniquesAssociationWithOptionCombination(cmd.techniqueIds, leftOption, rightOption)
			flash.message = "${message(code: 'tf.option.assiciations.updated', args: [leftOption.name, rightOption.name])}"
			redirect(action:"listAssociatedTechniques", params:[leftOption:leftOption.id, rightOption:rightOption.id])
		} catch (IllegalArgumentException ex) {
			log.error "Error in updating associations for techniques", ex
			flash.message="${message(code: 'tf.ajax.error', args: ['IllegalArgumentException', ex.message])}"
			render(template:"/templates/ajaxError")
		}
	}
}
