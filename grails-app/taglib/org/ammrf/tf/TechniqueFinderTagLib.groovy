package org.ammrf.tf

import grails.util.GrailsNameUtils;

/**
 * Technique Finder custom tags
 * 
 * @author Andrey Chernyshov
 */
class TechniqueFinderTagLib {
	def techniqueService
	def staticContentService
	
	/**
	 * Lists associated techniques of the provided object (Location, Review, etc.)
	 * 
	 * @params object - object to list techniques for
	 */
	def listAssociatedTechiques = {attrs, body ->
		def techniques = techniqueService.findTechniquesByRelation(attrs.object)
		out << "<ul>"
		techniques.each { out << "<li><a href='${createLink(controller:'techniqueAdmin', action:'show', id:it.id)}'>$it.name</a></li>" }
		out << "</ul>"
	}
	
	/**
	 * Creates previous and next links
	 * 
	 * @params controller, action, currentObject, sort
	 */
	def navigationLinks = {attrs, body ->
		def idList = getListForObject(attrs.currentObject, attrs.sort)
		def currentIndex = idList.indexOf(attrs.currentObject.id)
		
		if(currentIndex > -1) {
			if(currentIndex > 0) {
				out << createLink(attrs, idList.get(currentIndex - 1), '< previous')
			}
			if(currentIndex < idList.size() - 1) {
				if(currentIndex > 0) {
					out << " | "
				}
				out << createLink(attrs, idList.get(currentIndex + 1), 'next >')
			}
		}
	}
	
	/**
	 * Reads static content from DB
	 * 
	 * @params code - static content key
	 */
	def staticContent = {attrs, body ->
		def code = attrs.code
		if(code) {
			out << staticContentService.findText(code)
		} else {
			out << ''
		}
	}

	/**
	*
	*/
	def valueToJavaScript = {attrs, body ->
		def value = attrs.value
		try {
			int val = Integer.parseInt(value)
			out << val
                } catch (Exception e) {
			value = GspUtils.cleanString(value)
			out << ("'" + value + "'")
                }
        }
	
	private def createLink (attrs, id, text) {
		return "<a href='${createLink(controller:attrs.controller, action:attrs.action, id:id)}'>$text</a>"
	}
	
	private def getListForObject(object, sort) {
		if(object instanceof Option) {
			return  Option.executeQuery("select id from Option where science=:science and type=:type order by $sort", [science:object.science, type:object.type])
		} else {
			def domainObjectName = GrailsNameUtils.getShortName(object.class)
			return object.class.executeQuery("select id from $domainObjectName order by $sort")
		}
	}
}
