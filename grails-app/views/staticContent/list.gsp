
<%@ page import="org.ammrf.tf.StaticContent" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'staticContent.label', default: 'StaticContent')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
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
                            <g:sortableColumn property="name" title="${message(code: 'staticContent.name.label', default: 'Name')}" />
                            <g:sortableColumn property="text" title="${message(code: 'staticContent.text.label', default: 'Text')}" />
                            <th colspan="2">${message(code: 'staticContent.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${staticContentInstanceList}" status="i" var="staticContentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${message(code: "$staticContentInstance.name")}</td>
                            <td>${fieldValue(bean: staticContentInstance, field: "text")}</td>
                            <g:render template="/templates/actions" model="${[beanInstance:staticContentInstance, actions:['view', 'edit']]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
