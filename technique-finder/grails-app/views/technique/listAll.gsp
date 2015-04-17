<%@page import="org.ammrf.tf.Science"%>
<%@page import="org.ammrf.tf.MediaSection"%>
<%@ page import="org.ammrf.tf.GspUtils"%>

    <head>
        <meta name="layout" content="public" />
        <g:set var="page" value="${message(code: 'tf.allTechniques.title', default: 'Available techniques')}" />
        <title><g:message code="tf.title" args="[page]" /></title>
    </head>
    <content tag="body">

    <div id="content">
			
          <span class="nav_buttons">
              <ul class="right">
                <li id="button_backStart"><g:link controller="homePage"/></li>
              </ul>
          </span>
           
        <div class="clear"></div>
        
           
        <h1>List of available techniques</h1>

        <g:if test="${flash.message}">
        	<div class="message">${flash.message}</div>
        	<div class="clear"/>
        </g:if>
        <g:else>
        	
	        <g:if test="${techniques}">
			 <ul>	        
	         <g:each var="technique" in="${techniques}">
				<li><g:link action="show" id="${technique.id}" elementId="Technique_${technique.id}">${technique?.name?.encodeAsHTML()}</g:link></li>		        
	         </g:each>
	         </ul>
	        </g:if>
	        <g:else>
	        	<g:message code="tf.techniques.notFound" default="Nothing found"/>
	        </g:else>
        </g:else>
        
        

        <p><span style="margin: 10px 0 10px 0;"><a class="top" href="#top" title="top">TOP</a></span></p>

    </div>        
    </content>

