<%@ page import="org.ammrf.tf.Media" %>
<%@ page import="org.ammrf.tf.MediaType" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span class="menuButton"><g:link class="create" action="create" params="${[mediaType:MediaType.IMAGE]}"><g:message code="default.new.label" args="['Image']" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="${[mediaType:MediaType.MOVIE]}"><g:message code="default.new.label" args="['Movie']" /></g:link></span>
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
                        	<th>${message(code: 'media.thumbnail.label', default: 'Thumbnail')}</th>
                        	<th>${message(code: 'media.name.label', default: 'Name')}<br />
                        	${message(code: 'media.dimensions.label', default: 'Dimensions')}</th>
                        	<th>${message(code: 'media.caption.label', default: 'Caption')}</th>
                                <th colspan="3">${message(code: 'location.actions.label', default: 'Actions')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${instanceList}" status="i" var="instance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><a name="${fieldValue(bean: instance, field: "id")}"><g:render template="show" model="${[medium:instance, format:'thumbnail']}"/></a></td>
                            <td>
                                ${fieldValue(bean: instance, field: "name")} <br />
                                ${instance.original?.width}x${instance.original?.height}</td>

                            <td>${fieldValue(bean: instance, field: "caption")}</td>

                            <g:render template="/templates/actions" model="${[beanInstance:instance, actions:['view', 'edit', 'delete'], loopStatus:i, listSize:instanceList.size]}"/>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        </div>
   </body>
</html>
