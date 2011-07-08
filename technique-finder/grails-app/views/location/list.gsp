
<%@ page import="org.ammrf.tf.Location" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'location.label', default: 'Location')}" />
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
                        	<th>${message(code: 'location.institution.label', default: 'Institution')}</th>
                        	<th>${message(code: 'location.centerName.label', default: 'Center Name')}</th>
                        	<th>${message(code: 'location.address.label', default: 'Address')}</th>
                            <th colspan="4">${message(code: 'location.actions.label', default: 'Actions')}</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${locationInstanceList}" status="i" var="locationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: locationInstance, field: "institution")}</td>
                        
                            <td>${fieldValue(bean: locationInstance, field: "centerName")}</td>

                            <td>${fieldValue(bean: locationInstance, field: "address")}</td>

                            <g:render template="/templates/actions" model="${[beanInstance:locationInstance, actions:['view', 'edit', 'delete', 'move'], loopStatus:i, listSize:locationInstanceList.size]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
