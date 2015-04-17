
<%@ page import="org.ammrf.tf.Contact" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${contactInstance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="contact" action="show" currentObject="${contactInstance}" sort="location.priority asc, name"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/templates/showButtons" model="${[instance:contactInstance]}"/>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "title")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.position.label" default="Position" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "position")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.telephone.label" default="Telephone" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "telephone")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "email")}</td>
                            
                        </tr>
                    
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.techniqueContact.label" default="Technique Contact" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${contactInstance?.techniqueContact}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.location.label" default="Location" /></td>
                            
                            <td valign="top" class="value"><g:link controller="location" action="show" id="${contactInstance?.location?.id}">${contactInstance?.location?.institution?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.associatedTechniques.label" default="Associated Techniques" /></td>
                            
                            <td valign="top" class="value"><g:listAssociatedTechiques object="${contactInstance}"/></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <g:render template="/templates/showButtons" model="${[instance:contactInstance]}"/>
        </div>
    </body>
</html>
