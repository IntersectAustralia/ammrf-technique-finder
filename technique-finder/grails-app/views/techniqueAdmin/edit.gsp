<%@ page import="org.ammrf.tf.Technique" %>
<%@ page import="org.ammrf.tf.Contact" %>
<%@ page import="org.ammrf.tf.CaseStudy" %>
<%@ page import="org.ammrf.tf.Review" %>
<%@ page import="org.ammrf.tf.MediaSection"%>
<%@ page import="org.ammrf.tf.GspUtils"%>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'technique.label', default: 'Technique')}" />
        <title><g:if test="${instance?.id}"><g:message code="default.edit.label" args="[entityName]" /></g:if><g:else><g:message code="default.create.label" args="[entityName]" /></g:else></title>
        <jq:plugin name="jquery-ui"/>
        <g:javascript src="media-association.js"/>
        <g:render template="/templates/ckeditor"/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${instance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:if test="${instance?.id}"><g:message code="default.edit.label" args="[entityName]" /></g:if><g:else><g:message code="default.create.label" args="[entityName]" /></g:else></h1>
            <g:if test="${instance?.id}">
            	<g:navigationLinks controller="techniqueAdmin" action="edit" currentObject="${instance}" sort="name"/>
            </g:if>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${instance}">
            <div class="errors">
                <g:renderErrors bean="${instance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="${(instance?.id ? 'update' : 'save')}" method="post">
            	<g:render template="/templates/editButtons" model="${[instance:instance]}"/>
                <g:if test="${instance?.id}">
                <g:hiddenField name="id" value="${instance?.id}" />
                <g:hiddenField name="version" value="${instance?.version}" />
                </g:if>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="technique.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'name', 'errors')}">
                                    <g:textArea rows="3" name="name" value="${instance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="alternativeNames"><g:message code="technique.alternativeNames.label" default="Alternative names" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'alternativeNames', 'errors')}">
                                    <g:textArea rows="3" name="alternativeNames" value="${instance?.alternativeNames?.encodeAsHTML()}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="summary"><g:message code="technique.summary.label" default="Summary" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'summary', 'errors')}">
                                    <g:textArea name="summary" value="${instance?.summary}"/>
                                    <script type="text/javascript">CKEDITOR.replace('summary', {bodyId: 'summaryEditor'});</script>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="technique.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${instance?.description}"/>
                                    <script type="text/javascript">CKEDITOR.replace('description', {bodyId: 'descriptionEditor'});</script>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="keywords"><g:message code="technique.keywords.label" default="Keywords" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'keywords', 'errors')}">
                                    <g:textArea rows="3" name="keywords" value="${instance?.keywords?.encodeAsHTML()}" />
                                </td>
                            </tr>
                        
			    <g:each in="${MediaSection.values()}" var="section">
                            <!-- note: field value populated in javascript onsubmit; 
                                 class and id are used: class as selector for jquery and id to grab the corresponding 'section' 
                                 value of the variable -->
                            <input type="hidden" name="media_${section}_Ids" value="" class="mediaSection" id="${section}"/>
                            <tr class="prop _${section}">
                                <td valign="top" class="name">
                                    <label for="keywords"><g:message code="technique.section.label"
                                        default="Media examples for {0}" args="[section]"/></label>
                                </td>
                                <td valign="top" class="value">
					<table id="mediaList" style="width:600px;">
					    <thead>
						<tr>
							<th>${message(code: 'media.thumbnail.label', default: 'Thumbnail')}</th>
							<th>${message(code: 'media.name.label', default: 'Name')}<br />
							${message(code: 'media.dimensions.label', default: 'Dimensions')}</th>
							<th>${message(code: 'media.caption.label', default: 'Caption')}</th>
							<th>${message(code: 'media.actions.label', default: 'Actions')}</th>
						</tr>
					    </thead>
					    <tbody class="sortableContent">
                                            <g:if test="${mediaMap[section] != null && mediaMap[section].size() != 0}">
					        <g:each in="${mediaMap[section]}" var="medium">
						    <g:render template="imageRow" model="${[medium:medium,section:section]}"/>
					        </g:each>
                                            </g:if>
					    </tbody>
					</table>
			               <ul class="action_links">
			                   <li><a id="addMediaButton" href="#" onclick="${'listMedia(\''+createLink(action:'listMedia') + '\',\'' + section + '\')'}"
                                              ><span class="add"></span>Add Media for ${section}<div></a></li>
			               </ul>
                                </td>
                            </tr>
                            </g:each>

			    <g:each in="${['contacts','caseStudies', 'reviews']}" var="type">
				    <g:render template="associatesTable" 
					model='${associatesOptions[type].plus([objects:instance?."$type", type:type])}' />
			    </g:each>

                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/editButtons" model="${[instance:instance]}"/>
            </g:form>
        </div>
            <div id="resourcesDialog" title="Available Resources"></div>
            <div id="errorDialog" title="Error"></div>
            <script type="text/javascript">
                window.messages = {}
		window.messages.requestError = '<g:message code="technique.association.requestError" default="Request have failed."/>';
		window.messages.requestEmpty = '<g:message code="technique.association.requestError" default="Result set is empy. May be, you cannot associate more items."/>';
            </script>
    </body>
</html>
