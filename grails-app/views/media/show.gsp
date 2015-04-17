<%@ page import="org.ammrf.tf.Media" %>
<%@ page import="org.ammrf.tf.MediaType" %>
<%@ page import="org.ammrf.tf.MediaSection" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <g:javascript src="swfobject.js"/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${instance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="${[mediaType:MediaType.IMAGE]}"><g:message code="default.new.label" args="['Image']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="${[mediaType:MediaType.MOVIE]}"><g:message code="default.new.label" args="['Movie']" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="media" action="show" currentObject="${instance}" sort="name"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/templates/showButtons" model="${[instance:instance]}"/>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="media.name.label" default="Name" /></td>
                            <td valign="top" class="value">${fieldValue(bean: instance, field: "name")}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="media.caption.label" default="Caption" /></td>
                            <td valign="top" class="value">${fieldValue(bean: instance, field: "caption")}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="media.caption.label" default="Media Type" /></td>
                            <td valign="top" class="value">${fieldValue(bean: instance, field: "mediaType")}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="media.dimensions.label" default="Dimensions" /></td>
                            <td valign="top" class="value">${instance?.original?.width}x${instance?.original?.height}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="media.originalLocation.label" default="Location" /></td>
                            <td valign="top" class="value">${instance?.original?.location}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="media.originalImage.label" default="Image" /></td>
                            <td valign="top" class="value"><g:render template="show" model="${[medium:instance, format:'admin-show']}"/></td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="media.associatedTechniques.label" default="Associated techniques" /></td>
                            <td valign="top" class="value"><ul>
                               <g:each in="${techniques}" var="technique">
                               <li>${technique.name}</li>
                               </g:each>
                               </ul>
                            </td>
                        </tr>

                    </tbody>
                </table>
            </div>
           <g:render template="/templates/showButtons" model="${[instance:instance]}"/>
        </div>
        </div>
   </body>
</html>
