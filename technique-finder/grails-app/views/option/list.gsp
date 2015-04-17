
<%@ page import="org.ammrf.tf.Option" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'option.label', default: 'Option')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create" params="${[science:params.science, type:params.type]}"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="tf.option.${params.science}.${params.type}.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>${message(code: 'option.name.label', default: 'Name')}</th>
                            <th colspan="3">${message(code: 'option.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${optionInstanceList}" status="i" var="optionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${fieldValue(bean: optionInstance, field: "name")}</td>
                            <g:render template="/templates/actions" model="${[beanInstance:optionInstance, actions:['edit', 'delete', 'move'], loopStatus:i, listSize:optionInstanceList.size]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
