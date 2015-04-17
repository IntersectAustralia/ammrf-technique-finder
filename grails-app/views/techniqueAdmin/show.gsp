
<%@ page import="org.ammrf.tf.Technique" %>
<%@ page import="org.ammrf.tf.MediaSection" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'technique.label', default: 'Technique')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${instance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1> 
            <g:link controller="technique" action="show" id="${instance.id}">[in public site]</g:link>
            <g:navigationLinks controller="techniqueAdmin" action="show" currentObject="${instance}" sort="name"/>
            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/templates/showButtons" model="${[instance:instance]}"/>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.name.label" default="name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: instance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.alternativeNames.label" default="Alternative names" /></td>
                            
                            <td valign="top" class="value">${instance?.alternativeNames?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.summary.label" default="Short description" /></td>
                            
                            <td valign="top" class="value">${instance?.summary}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${instance?.description}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.keywords.label" default="Keywords" /></td>
                            
                            <td valign="top" class="value">${instance?.keywords?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.contacts.label" default="Contacts" /></td>
                            
                            <td valign="top" class="value">
				<g:if test="${instance?.contacts.size()!=0}">
				<ul>
				<g:each in="${instance?.contacts}" var="contact">
					<li>${contact.title?.encodeAsHTML()}
						${contact.name?.encodeAsHTML()} -
						${contact.location?.institution?.encodeAsHTML()}</li>
				</g:each>
				</ul>
				</g:if>
				<g:else>
					<g:message code="technique.contacts.empty" default="Nothing" />
				</g:else>
			    </td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.caseStudies.label" default="Case studies" /></td>
                            
                            <td valign="top" class="value">
				<g:if test="${instance?.caseStudies.size()!=0}">
				<ul>
				<g:each in="${instance?.caseStudies}" var="caseStudy">
					<li>${caseStudy?.name?.encodeAsHTML()}</li>
				</g:each>
				</ul>
				</g:if>
				<g:else>
					<g:message code="technique.caseStudies.empty" default="Nothing" />
				</g:else>
			    </td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.reviews.label" default="References" /></td>
                            
                            <td valign="top" class="value">
				<g:if test="${instance?.reviews.size()!=0}">
				<ul>
				<g:each in="${instance?.reviews}" var="review">
					<li>${review.referenceNames?.encodeAsHTML()} ${review.title?.encodeAsHTML()}</li>
				</g:each>
				</ul>
				</g:if>
				<g:else>
					<g:message code="technique.reviews.empty" default="Nothing" />
				</g:else>
			    </td>
                        </tr>
                   
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technique.options.label" default="Associated options" /></td>
                            
                            <td valign="top" class="value">
				<g:if test="${instance?.options?.size()!=0}">
				<ul>
                                <g:set var="currentScience" value="null" />
				<g:each in="${instance?.options.sort {a, b -> a.left.science != b.left.science ? a.left.science <=> b.left.science : (a.left.priority != b.left.priority ? a.left.priority <=> b.left.priority : a.right.priority <=> b.right.priority)} }" var="optionCombination">
                                        <g:if test="${currentScience != optionCombination.left.science}">
                                        <b>${optionCombination.left.science.toString().toLowerCase()}:</b>
                                        <g:set var="currentScience" value="${optionCombination.left.science}" />
                                        </g:if>
					<li> ${optionCombination.left} - ${optionCombination.right}</li>
				</g:each>
				</ul>
				</g:if>
				<g:else>
					<g:message code="technique.options.empty" default="Nothing" />
				</g:else>
			    </td>
                        </tr>
                   
                        <g:each in="${MediaSection.values()}" var="section"> 
		                <tr class="prop">
		                    <td valign="top" class="name"><g:message code="technique.section.label"
                                        default="Media examples for {0}" args="[section]"/></td>
		                    
		                    <td valign="top" class="value">
                                    <g:if test="${mediaMap[section] != null && mediaMap[section].size() != 0}">
		                        <p>
					<g:each in="${mediaMap[section]}" var="medium">
		                            <g:render template="/media/show" model="${[medium:medium, format:'thumbnail', section:section]}"/>
					</g:each>
		                        </p>
				    </g:if>
				    <g:else>
					<g:message code="technique.section.empty" default="Nothing" />
				    </g:else>
				    </td>
		                </tr>
                        </g:each>
                                        
                    </tbody>
                </table>
            </div>
            <g:render template="/templates/showButtons" model="${[instance:instance]}"/>
        </div>
    </body>
</html>
