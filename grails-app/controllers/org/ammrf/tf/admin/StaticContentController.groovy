package org.ammrf.tf.admin

import javax.servlet.http.HttpServletResponse;

import org.ammrf.tf.StaticContent;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

@Secured(['ROLE_ADMIN'])
class StaticContentController {

	def scaffold = StaticContent
	
	def list = {
	    [staticContentInstanceList: StaticContent.list(sort:params.sort, order:params.order)]
	}
	
	def create = {
		response.sendError(HttpServletResponse.SC_NOT_FOUND, "$servletContext.contextPath$actionUri")
	}
	
	def delete = {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "$servletContext.contextPath$actionUri")
	}
}
