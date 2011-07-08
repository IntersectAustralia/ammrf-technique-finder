<%@ page import="org.ammrf.tf.Media" %>
<%@ page import="org.ammrf.tf.MediaType" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${instance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:if test="${instance?.id}"><g:message code="default.edit.label" args="[mediaType]" /></g:if><g:else><g:message code="default.create.label" args="[mediaType]" /></g:else></h1>
            <g:if test="${instance?.id}">
            	<g:navigationLinks controller="media" action="edit" currentObject="${instance}" sort="name"/>
            </g:if>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${instance}">
            <div class="errors">
                <g:renderErrors bean="${instance}" as="list" />
            </div>
            </g:hasErrors>
            
            <g:form action="${(instance?.id ? 'update' : 'save')}" method="post" enctype='multipart/form-data'>
                <g:render template="/templates/editButtons" model="${[instance:instance]}"/>
                <g:hiddenField name="mediaType" value="${mediaType}" />
                <g:if test="${instance?.id}">
                <g:hiddenField name="id" value="${instance?.id}" />
                <g:hiddenField name="version" value="${instance?.version}" />
                </g:if>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="media.caption.label" default="Caption" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'caption', 'errors')}">
                                    <g:textArea rows="3" name="caption" value="${instance?.caption}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="media.newFile.label" default="File" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'original', 'errors')}">
                                    <input type="file" name="newFile" />
                                </td>
                            </tr>

			    <g:if test="${mediaType == MediaType.MOVIE}">
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="media.thumbnailFile.label" default="Frame image" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: 'thumbnail', 'errors')}">
                                    <input type="file" name="thumbnailFile" />
                                </td>
                            </tr>
                            </g:if>

                        </tbody>
                   </table>
               </div>
                <g:render template="/templates/editButtons" model="${[instance:instance]}"/>
            </g:form>


        </div>
        </div>
   </body>
</html>
