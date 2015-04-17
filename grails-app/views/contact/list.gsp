
<%@ page import="org.ammrf.tf.Contact" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>${message(code: 'contact.name.label', default: 'Name')}</th>
                            <th>${message(code: 'contact.institution.label', default: 'Institution')}</th>
                            <th>${message(code: 'contact.techniqueContact.label', default: 'Technique Contact')}</th>
                            <th colspan="3">${message(code: 'contact.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contactInstanceList}" status="i" var="contactInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><a name="${fieldValue(bean: contactInstance, field: "id")}">${fieldValue(bean: contactInstance, field: "title")} ${fieldValue(bean: contactInstance, field: "name")}</a></td>
                            <td>${fieldValue(bean: contactInstance, field: "location.institution")}</td>
                            <td><g:formatBoolean boolean="${contactInstance.techniqueContact}" /></td>
                        	
                        	<g:render template="/templates/actions" model="${[beanInstance:contactInstance, actions:['view', 'edit', 'delete']]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
