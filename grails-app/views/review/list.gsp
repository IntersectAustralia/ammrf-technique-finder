
<%@ page import="org.ammrf.tf.Review" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'review.label', default: 'Review')}" />
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
                            <th>${message(code: 'review.referenceNames.label', default: 'Reference Names')}</th>
                        	<th>${message(code: 'review.title.label', default: 'Title')}</th>
                        	<th colspan="3">${message(code: 'review.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${reviewInstanceList}" status="i" var="reviewInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><a name="${fieldValue(bean: reviewInstance, field: "id")}">${fieldValue(bean: reviewInstance, field: "referenceNames")}</a></td>
                            <td>${fieldValue(bean: reviewInstance, field: "title")}</td>
                            <g:render template="/templates/actions" model="${[beanInstance:reviewInstance, actions:['view', 'edit', 'delete']]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
