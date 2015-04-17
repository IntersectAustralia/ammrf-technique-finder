package org.ammrf.tf

import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.compass.core.engine.SearchEngineQueryParseException;
import javax.servlet.http.Cookie;

/**
 * Technique controller for public part.
 * Used to list techniques and show details.
 * 
 * @author Andrey Chernyshov
 *
 */
class TechniqueController {
	
	def techniqueService
	def staticContentService
        def grailsApplication
	public static final String BACK_COOKIE_NAME = "backUrl"
	
	private def setBackCookie = { value, response ->
		def cookie = new Cookie(BACK_COOKIE_NAME, value)
		response.addCookie(cookie)
	}
	
	/**
	 * Lists techniques based on the selected choice combination. 
	 */
    def list = {
		def leftOption = Option.get(params.leftOption)
		if(!leftOption) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.left.label', default: 'Left side option'), params.leftOption])}"
			return
		}
		def rightOption = Option.get(params.rightOption)
		if(!rightOption) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'option.right.label', default: 'Right side option'), params.rightOption])}"
			return
		}
		
		def key = (leftOption.science == Science.BIOLOGY ? 'biologyChoices' : 'physicsChoices')
		def comparisonTitle = staticContentService.findText('tf.' + key + '.comparison.title')?.toLowerCase()
		def techniques = techniqueService.findTechniquesByOptionCombination(leftOption, rightOption, ['id', 'name', 'summary'])
		def resultsTitle = "$leftOption.name $comparisonTitle $rightOption.name"
		setBackCookie("$params.action?leftOption=$params.leftOption&rightOption=$params.rightOption", response)
		
		[techniques:techniques, leftOption:leftOption, rightOption:rightOption, resultsTitle:resultsTitle,
                     techniqueMedia:techniqueService.attachMedia(techniques)]
    }
	
	/**
	 * Lists all available techniques
	 */
	def listAll = {
		def techniques = techniqueService.findAllTechniques(['id', 'name'])
		setBackCookie("$params.action", response)
		[techniques:techniques]
	}
	
	def show = {
		def technique = Technique.get(params.id)
		if(!technique) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tf.technique.label', default: 'Technique'), params.id])}"
			return
		} else {
                        def outputMedia = techniqueService.getMediaInTechniqueSection(technique, MediaSection.OUTPUT)
                        def instrumentMedia = techniqueService.getMediaInTechniqueSection(technique, MediaSection.INSTRUMENT)
			[technique:technique, outputMedia: outputMedia, instrumentMedia: instrumentMedia]
		}
	}
	
	def search = {
		def techniques = []
		def query = params.q
		params.max = Math.min(params.max ? params.int('max') : 10, 20)
		def searchResult
		if(query) {
			try {
				searchResult = Technique.search(query, params)
				techniques = searchResult.results
				log.debug "searchResult: $searchResult"
			} catch (SearchEngineQueryParseException ex) {
	            log.error ex
	        } catch (TooManyClauses ex) {
	        	flash.message = "${message(code: 'tf.admin.search.toGeneral.message')}"
	        }
		}
		log.debug "Searched by query [$query], techniques found: $techniques"
		setBackCookie("$params.action?q=$params.q", response)
		
		render(view:'list', model:[techniques:techniques, resultsTitle:query, search:true, searchResult:searchResult, 
                    techniqueMedia:techniqueService.attachMedia(techniques)])
	}

}
