
<%@ page import="org.ammrf.tf.StaticContent" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'staticContent.label', default: 'StaticContent')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:render template="/templates/ckeditor"/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${staticContentInstance}">
            <div class="errors">
                <g:renderErrors bean="${staticContentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${staticContentInstance?.id}" />
                <g:hiddenField name="version" value="${staticContentInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="staticContent.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value">
                                    ${message(code: "$staticContentInstance.name")}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="text"><g:message code="staticContent.text.label" default="Text" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: staticContentInstance, field: 'text', 'errors')}">
                                    <g:textArea name="text" value="${staticContentInstance?.text}"/>
                                    <script type="text/javascript">CKEDITOR.replace('text', {bodyId: 'textEditor'});</script>
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
