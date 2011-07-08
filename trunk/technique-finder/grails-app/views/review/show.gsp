
<%@ page import="org.ammrf.tf.Review" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'review.label', default: 'Review')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${reviewInstance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="review" action="show" currentObject="${reviewInstance}" sort="title"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/templates/showButtons" model="${[instance:reviewInstance]}"/>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="review.referenceNames.label" default="Reference Names" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: reviewInstance, field: "referenceNames")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="review.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: reviewInstance, field: "title")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="review.fullReference.label" default="Full Reference" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: reviewInstance, field: "fullReference")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="review.url.label" default="Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: reviewInstance, field: "url")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="review.associatedTechniques.label" default="Associated Techniques" /></td>
                            
                            <td valign="top" class="value"><g:listAssociatedTechiques object="${reviewInstance}"/></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <g:render template="/templates/showButtons" model="${[instance:reviewInstance]}"/>
        </div>
    </body>
</html>
