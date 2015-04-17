
<%@ page import="org.ammrf.tf.StaticContent" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'staticContent.label', default: 'StaticContent')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="staticContent.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${message(code: "$staticContentInstance.name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="staticContent.text.label" default="Text" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: staticContentInstance, field: "text")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <g:form>
                <g:hiddenField name="id" value="${staticContentInstance?.id}" />
                <g:render template="/templates/formButtons" model="${[actions:['edit']]}"/>
            </g:form>
        </div>
    </body>
</html>
