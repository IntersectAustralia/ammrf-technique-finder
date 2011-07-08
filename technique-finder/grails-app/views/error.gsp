<%@page import="javax.servlet.http.HttpServletResponse"%>
<% response.status = HttpServletResponse.SC_NOT_FOUND %>
<head>
    <g:set var="page" value="${message(code: 'tf.errorPage.title', default: 'Resource Not Found')}" />
    <title><g:message code="tf.title" args="[page]" /></title>
</head>
<g:applyLayout name="public">
 <content tag="body">
    <div id="content">
		<h2>Requested Resource Not Found</h2><p>Sorry, we did not find the resource you were looking for.</p>      
	</div>
  </content>
</g:applyLayout>