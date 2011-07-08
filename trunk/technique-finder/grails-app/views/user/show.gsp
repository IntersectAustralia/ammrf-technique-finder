<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <g:ifAllGranted role="ROLE_SUPER">
            	<g:if test="${superAdmin}">
	            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
	            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
	            </g:if>
            </g:ifAllGranted>
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
                            <td valign="top" class="name"><g:message code="user.username.label" default="Username" /></td>
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "username")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.fullName.label" default="Full name" /></td>
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "fullName")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.secondEmail.label" default="Additional email address" /></td>
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "secondEmail")}</td>
                        </tr>
                        <g:ifAllGranted role="ROLE_SUPER">
			            	<g:if test="${superAdmin}">
				            	<tr class="prop">
		                            <td valign="top" class="name"><g:message code="user.isSuper.label" default="Super admin" /></td>
		                            <td valign="top" class="value"><g:formatBoolean boolean="${userInstance.isSuper}"/></td>
		                        </tr>
				            </g:if>
			            </g:ifAllGranted>
                    </tbody>
                </table>
            </div>
            <g:form>
                <g:hiddenField name="id" value="${userInstance?.id}" />
                <g:if test="${superAdmin}">
            		<span class="buttons"><g:actionSubmit class="edit" action="editAsSuper" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
            	</g:if>
            	<g:else>
            		<g:render template="/templates/formButtons" model="${[actions:['edit']]}"/>
            	</g:else>
            	<g:ifAllGranted role="ROLE_SUPER">
            		<g:if test="${!loggedInUserInfo(field:'id').toString().equals(userInstance?.id.toString())}">
            			<g:render template="/templates/formButtons" model="${[actions:['delete']]}"/>
            		</g:if>
            	</g:ifAllGranted>
            </g:form>
        </div>
    </body>
</html>
