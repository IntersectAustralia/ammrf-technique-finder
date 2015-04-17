
<%@ page import="org.ammrf.tf.Location" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'location.label', default: 'Location')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="location" action="show" currentObject="${locationInstance}" sort="priority"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/templates/showButtons" model="${[instance:locationInstance]}"/>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="location.institution.label" default="Institution" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: locationInstance, field: "institution")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="location.state.label" default="State" /></td>
                            
                            <td valign="top" class="value">${locationInstance?.state?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="location.status.label" default="Status" /></td>
                            
                            <td valign="top" class="value">${locationInstance?.status?.name?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="location.address.label" default="Address" /></td>
                            
                            <td valign="top" class="value"><pre>${fieldValue(bean: locationInstance, field: "address")}</pre></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="location.centerName.label" default="Center Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: locationInstance, field: "centerName")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="location.associatedTechniques.label" default="Associated Techniques" /></td>
                            
                            <td valign="top" class="value"><g:listAssociatedTechiques object="${locationInstance}"/></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <g:render template="/templates/showButtons" model="${[instance:locationInstance]}"/>
        </div>
    </body>
</html>
