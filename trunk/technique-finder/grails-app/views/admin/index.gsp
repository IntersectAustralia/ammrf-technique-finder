<%@ page import="org.ammrf.tf.Science" %>
<%@ page import="org.ammrf.tf.OptionType" %>

<html>
    <head>
        <title>Home</title>
		<meta name="layout" content="main" />
    </head>
    <div class="body">
      <h1>AMMRF Technique Finder Admin Area</h1>
      
      <div class="dialog">
      	<p>Welcome to the AMMRF Technique Finder Admin Area. This is where you can edit the technique finder contacts, locations as well as edit the description of the techniques themselves.</p>
      	
      	<g:if test="${flash.message}">
        	<div class="message">${flash.message}</div>
        </g:if>
        
      	<ul class="menu_links">
      		<li><g:link controller="techniqueAdmin">Techniques</g:link></li>
        	<li><g:link controller="media">Images and movies</g:link></li>
        	<li><g:link controller="review">References</g:link></li>
        	<li><g:link controller="caseStudy">Case studies</g:link></li>
        	<li><g:link controller="contact">Contacts</g:link></li>
        	<li><g:link controller="location">Locations</g:link></li>
        </ul>
      	<ul class="menu_links">
        	<li><g:link controller="option" params="${[science:Science.BIOLOGY, type:OptionType.LEFT]}">Bio options - Left</g:link></li>
        	<li><g:link controller="option" params="${[science:Science.BIOLOGY, type:OptionType.RIGHT]}">Bio options - Right</g:link></li>
        	<li><g:link controller="option" params="${[science:Science.PHYSICS, type:OptionType.LEFT]}">Phys options - Left</g:link></li>
        	<li><g:link controller="option" params="${[science:Science.PHYSICS, type:OptionType.RIGHT]}">Phys options - Right</g:link></li>
        	<li><g:link controller="optionsAssociation" action="listBioOptions">Bio associations</g:link></li>
        	<li><g:link controller="optionsAssociation" action="listPhysicsOptions">Phys associations</g:link></li>
        </ul>
      	<ul class="menu_links">
        	<g:ifAllGranted role="ROLE_SUPER">
        		<li><g:link controller="user">Manage Accounts</g:link></li>
        	</g:ifAllGranted>
        	<li><g:link controller="user" action="show" id="${loggedInUserInfo(field:'id')}">Manage My Account</g:link></li>
        	<li><g:link controller="staticContent">Static content</g:link></li>
        </ul>

      </div><!-- /dialog -->
      
    </div><!-- /body -->
</html>
