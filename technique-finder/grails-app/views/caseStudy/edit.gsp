
<%@ page import="org.ammrf.tf.CaseStudy" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'caseStudy.label', default: 'CaseStudy')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:render template="/templates/ckeditor"/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list" fragment="${caseStudyInstance?.id}"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:navigationLinks controller="caseStudy" action="edit" currentObject="${caseStudyInstance}" sort="name"/>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${caseStudyInstance}">
            <div class="errors">
                <g:renderErrors bean="${caseStudyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:render template="/templates/editButtons" model="${[instance:caseStudyInstance]}"/>
                <g:hiddenField name="id" value="${caseStudyInstance?.id}" />
                <g:hiddenField name="version" value="${caseStudyInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="caseStudy.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseStudyInstance, field: 'name', 'errors')}">
                                	<g:textArea name="name" value="${caseStudyInstance?.name}"/>
                                    <script type="text/javascript">CKEDITOR.replace('name', {bodyId: 'nameEditor'});</script>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="url"><g:message code="caseStudy.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: caseStudyInstance, field: 'url', 'errors')}">
                                    <g:textArea name="url" value="${caseStudyInstance?.url}" />
                                </td>
                            </tr>
                            
                            <g:if test="${caseStudyInstance?.id}">
                            <tr class="prop">
                            <td valign="top" class="name"><g:message code="caseStudy.associatedTechniques.label" default="Associated Techniques" /></td>
                            
                            <td valign="top" class="value"><g:listAssociatedTechiques object="${caseStudyInstance}"/></td>
                            </g:if>
                            
                        </tr>
                        
                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/editButtons" model="${[instance:caseStudyInstance]}"/>
            </g:form>
        </div>
    </body>
</html>
