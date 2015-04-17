
<%@ page import="org.ammrf.tf.CaseStudy" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'caseStudy.label', default: 'CaseStudy')}" />
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
                        	<th>${message(code: 'caseStudy.name.label', default: 'Name')}</th>
                            <th>${message(code: 'caseStudy.url.label', default: 'URL')}</th>
                            <th colspan="2">${message(code: 'caseStudy.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${caseStudyInstanceList}" status="i" var="caseStudyInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><a name="${fieldValue(bean: caseStudyInstance, field: "id")}">${fieldValue(bean: caseStudyInstance, field: "name")}</a></td>
                            <td>${fieldValue(bean: caseStudyInstance, field: "url")}</td>
                            <g:render template="/templates/actions" model="${[beanInstance:caseStudyInstance, actions:['edit', 'delete']]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
