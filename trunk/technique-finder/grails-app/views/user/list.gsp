<%@ page import="org.ammrf.tf.security.User" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="fullName" title="${message(code: 'user.fullName.label', default: 'Full Name')}" />
                            <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" />
                            <th>${message(code: 'user.isSuper.label', default: 'Super admin')}</th>
                        	<th colspan="3">${message(code: 'user.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userInstanceList}" status="i" var="userInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${fieldValue(bean: userInstance, field: "fullName")}</td>
                            <td>${fieldValue(bean: userInstance, field: "username")}</td>
                            <td><g:formatBoolean boolean="${userInstance.isSuper}"/></td>
                            
                            <td>
	                            <ul class="action_links">
									<li><g:link action="show" id="${userInstance?.id}" params="[superAdmin:true]"><span class="view"></span>View</g:link></li>
								</ul>
							</td>
							
							<td>
	                            <ul class="action_links">
									<li><g:link action="editAsSuper" id="${userInstance?.id}"><span class="edit"></span>Edit</g:link></li>
								</ul>
                        	</td>
                        	<g:if test="${!loggedInUserInfo(field:'id').toString().equals(userInstance?.id.toString())}">
		            			<g:render template="/templates/actions" model="${[beanInstance:userInstance, actions:['delete']]}"/>
		            		</g:if>
		            		<g:else>
		            			<td/>
		            		</g:else>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
