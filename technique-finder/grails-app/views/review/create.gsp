
<%@ page import="org.ammrf.tf.Review" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'review.label', default: 'Review')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:render template="/templates/ckeditor"/>
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
            <g:hasErrors bean="${reviewInstance}">
            <div class="errors">
                <g:renderErrors bean="${reviewInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <g:render template="/templates/editButtons" model="${[instance:reviewInstance]}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="referenceNames"><g:message code="review.referenceNames.label" default="Reference Names" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reviewInstance, field: 'referenceNames', 'errors')}">
                                    <g:textArea rows="3" name="referenceNames" value="${reviewInstance?.referenceNames}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title"><g:message code="review.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reviewInstance, field: 'title', 'errors')}">
                                    <g:textArea name="title" value="${reviewInstance?.title}"/>
                                    <script type="text/javascript">CKEDITOR.replace('title', {bodyId: 'titleEditor'});</script>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fullReference"><g:message code="review.fullReference.label" default="Full Reference" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reviewInstance, field: 'fullReference', 'errors')}">
                                    <g:textArea name="fullReference" value="${reviewInstance?.fullReference}"/>
                                    <script type="text/javascript">CKEDITOR.replace('fullReference', {bodyId: 'fullReferenceEditor'});</script>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="url"><g:message code="review.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reviewInstance, field: 'url', 'errors')}">
                                    <g:textArea name="url" value="${reviewInstance?.url}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/editButtons" model="${[instance:reviewInstance]}"/>
            </g:form>
        </div>
    </body>
</html>
