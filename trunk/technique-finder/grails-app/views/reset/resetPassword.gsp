<head>
<title>Reset password form</title>
<meta name='layout' content='main' />
</head>

<body>
	<div class="nav">
      <span class="menuButton">Reset password form</span>
    </div> <!-- /nav -->
    
        <div class="body">
            <h1><g:message code="tf.reset.pageTitle"/></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" action="update">
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
                                  <label for="password"><g:message code="user.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" value="" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="passwordConfirmation"><g:message code="user.password.confirm.label" default="Confirm password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'passwordConfirmation', 'errors')}">
                                    <g:passwordField name="passwordConfirmation" value="" />
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
                <g:render template="/templates/formButtons" model="${[actions:['update']]}"/>
            </g:form>
        </div>
</body>
