
<%@ page import="org.ammrf.tf.Location" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'location.label', default: 'Location')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="location" action="edit" currentObject="${locationInstance}" sort="priority"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${locationInstance}">
            <div class="errors">
                <g:renderErrors bean="${locationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:render template="/templates/editButtons" model="${[instance:locationInstance]}"/>
                <g:hiddenField name="id" value="${locationInstance?.id}" />
                <g:hiddenField name="version" value="${locationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="institution"><g:message code="location.institution.label" default="Institution" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: locationInstance, field: 'institution', 'errors')}">
                                    <g:textField name="institution" value="${locationInstance?.institution}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="state"><g:message code="location.state.label" default="State" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: locationInstance, field: 'state', 'errors')}">
                                    <g:select name="state" from="${org.ammrf.tf.State?.values()}" value="${locationInstance?.state}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="status"><g:message code="location.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: locationInstance, field: 'status', 'errors')}">
                                    <g:select name="status" from="${org.ammrf.tf.LocationStatus?.values()}" value="${locationInstance?.status}" optionValue="name" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="address"><g:message code="location.address.label" default="Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: locationInstance, field: 'address', 'errors')}">
                                    <g:textArea name="address" rows="4" value="${locationInstance?.address}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="centerName"><g:message code="location.centerName.label" default="Center Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: locationInstance, field: 'centerName', 'errors')}">
                                    <g:textField name="centerName" value="${locationInstance?.centerName}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/editButtons" model="${[instance:locationInstance]}"/>
            </g:form>
        </div>
    </body>
</html>
