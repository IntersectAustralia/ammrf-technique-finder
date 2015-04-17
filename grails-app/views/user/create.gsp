<%@ page import="org.ammrf.tf.security.User" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username"><g:message code="user.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" value="${userInstance?.username}" />
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
                                    <g:checkBox name="isSuper" value="${userInstance?.isSuper}" />
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
                <g:render template="/templates/formButtons" model="${[actions:['create']]}"/>
            </g:form>
        </div>
    </body>
</html>
