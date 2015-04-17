<%@ page import="org.ammrf.tf.security.User" %>
<%@page import="org.ammrf.tf.security.UserController"%>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
	        <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
	        <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${userInstance?.id}" />
                <g:hiddenField name="version" value="${userInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="username"><g:message code="user.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value">
                                	${fieldValue(bean: userInstance, field: "username")}
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fullName"><g:message code="user.fullName.label" default="Full name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'fullName', 'errors')}">
                                    <g:textField name="fullName" value="${userInstance?.fullName}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="enabled"><g:message code="user.isSuper.label" default="Super administrator" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'isSuper', 'errors')}">
                                    <g:checkBox name="isSuper" value="${userInstance?.isSuper}" disabled="${1 == userInstance?.id}"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <span class="buttons"><g:actionSubmit class="save" action="updateAsSuper" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
            </g:form>
        </div>
    </body>
</html>
