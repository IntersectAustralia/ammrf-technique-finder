
<%@ page import="org.ammrf.tf.Option" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'option.label', default: 'Option')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list" params="${[science:optionInstance?.science, type:optionInstance?.type]}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="${[science:optionInstance?.science, type:optionInstance?.type]}"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="option" action="edit" currentObject="${optionInstance}" sort="priority"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${optionInstance}">
            <div class="errors">
                <g:renderErrors bean="${optionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:render template="/templates/editButtons" model="${[instance:optionInstance]}"/>
                <g:hiddenField name="id" value="${optionInstance?.id}" />
                <g:hiddenField name="version" value="${optionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="option.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: optionInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${optionInstance?.name}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/editButtons" model="${[instance:optionInstance]}"/>
            </g:form>
        </div>
    </body>
</html>
