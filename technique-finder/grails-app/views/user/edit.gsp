<%@ page import="org.ammrf.tf.security.User" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
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
                                  <label for="password"><g:message code="user.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" value="${userInstance?.password}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="passwordConfirmation"><g:message code="user.password.confirm.label" default="Confirm password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'passwordConfirmation', 'errors')}">
                                    <g:passwordField name="passwordConfirmation" value="${userInstance?.passwordConfirmation}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="secondEmail"><g:message code="user.secondEmail.label" default="Additional email address " /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'secondEmail', 'errors')}">
                                    <g:textField name="secondEmail" value="${userInstance?.secondEmail}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="secondEmailConfirmation"><g:message code="user.secondEmail.confirm.label" default="Confirm additional email address " /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'secondEmailConfirmation', 'errors')}">
                                    <g:textField name="secondEmailConfirmation" value="${userInstance?.secondEmailConfirmation}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/formButtons" model="${[actions:['update']]}"/>
            </g:form>
        </div>
    </body>
</html>
