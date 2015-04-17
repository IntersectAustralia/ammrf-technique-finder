<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
    <head>
        <title>TF Admin | <g:layoutTitle default="AMMRF" /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        
        <link rel="shortcut icon" href="${resource(dir:'images/ammrf',file:'favicon.ico')}" type="image/x-icon" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'humanmsg.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css/smoothness',file:'jquery-ui-1.8.custom.css')}" />
        
        <div class="logo">
	        <a href="${createLink(uri: '/')}" title="Go to public site" tabindex="0"><img src="${resource(dir:'images/ammrf',file:'td_logo.png')}" border="0" alt="AMMRF Admin"></a>
	        <g:isLoggedIn>  
	        	<div><span class="identity">You are logged in as <strong><g:loggedInUserInfo field="username"/></strong> | <g:link controller="logout">Logout</g:link></span></div>
	        </g:isLoggedIn>
	        <br>
      	</div><!-- /logo -->
      	
      	<g:javascript library="jquery"/>
      	<g:javascript src="application.js"/>
      	<tm:resources jquery="false" css="false"/>
        <g:layoutHead />
    </head>
    <body style="height:100%;">
        <g:layoutBody />
		<div class="clear"></div>
		   
	    <div class="footer">
	    	<span style="float: left;">v<g:meta name="app.version"/></span>
	    	&copy; 2010 AMMRF &nbsp;|&nbsp;
	   		<a class="intersect_logo_link" href="http://www.intersect.org.au/" target="_blank"></a>Developed by <a href="http://www.intersect.org.au/" target="_blank">Intersect Australia</a>

		<g:staticContent code="tf.tracking.ammrf"/>
		<g:staticContent code="tf.tracking.intersect"/>
			
	    </div> <!-- /footer -->
    </body>
</html>
