
<%@ page import="org.ammrf.tf.Contact" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${contactInstance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="contact" action="edit" currentObject="${contactInstance}" sort="location.priority asc, name"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${contactInstance}">
            <div class="errors">
                <g:renderErrors bean="${contactInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:render template="/templates/editButtons" model="${[instance:contactInstance]}"/>
                <g:hiddenField name="id" value="${contactInstance?.id}" />
                <g:hiddenField name="version" value="${contactInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title"><g:message code="contact.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contactInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" maxlength="25" value="${contactInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="contact.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contactInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${contactInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="position"><g:message code="contact.position.label" default="Position" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contactInstance, field: 'position', 'errors')}">
                                    <g:textField name="position" value="${contactInstance?.position}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="telephone"><g:message code="contact.telephone.label" default="Telephone" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contactInstance, field: 'telephone', 'errors')}">
                                    <g:textField name="telephone" value="${contactInstance?.telephone}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="contact.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contactInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${contactInstance?.email}" />
                                </td>
                            </tr>
                        
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="techniqueContact"><g:message code="contact.techniqueContact.label" default="Technique Contact" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contactInstance, field: 'techniqueContact', 'errors')}">
                                    <g:checkBox name="techniqueContact" value="${contactInstance?.techniqueContact}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="location"><g:message code="contact.location.label" default="Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contactInstance, field: 'location', 'errors')}">
                                    <g:select name="location.id" from="${org.ammrf.tf.Location.list()}" optionKey="id" value="${contactInstance?.location?.id}" optionValue="institution" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/editButtons" model="${[instance:contactInstance]}"/>
            </g:form>
        </div>
    </body>
</html>
