<%@ page import="org.ammrf.tf.Technique" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'technique.label', default: 'Technique')}" />
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
                        	<th>${message(code: 'technique.name.label', default: 'Name')}</th>
                        	<th>${message(code: 'technique.summary.label', default: 'Short description')}</th>
                        	<th>${message(code: 'technique.keywords.label', default: 'Keywords')}</th>
                        	<th>${message(code: 'technique.alternativeNames.label', default: 'Alternative names')}</th>
                            <th colspan="3">${message(code: 'technique.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${instanceList}" status="i" var="instance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><a name="${fieldValue(bean: instance, field: "id")}">${fieldValue(bean: instance, field: "name")}</a></td>
                        
                            <td>${fieldValue(bean: instance, field: "summary")}</td>

                            <td>${fieldValue(bean: instance, field: "keywords")}</td>

                            <td>${fieldValue(bean: instance, field: "alternativeNames")}</td>

                            <g:render template="/templates/actions" model="${[beanInstance:instance, actions:['view', 'edit', 'delete'], loopStatus:i, listSize:instanceList.size]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
