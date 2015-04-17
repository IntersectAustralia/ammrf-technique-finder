<%@page import="org.ammrf.tf.MediaSection"%>
<%@page import="org.ammrf.tf.TechniqueController"%>

<head>
        <meta name="layout" content="public" />
        <g:set var="page" value="${message(code: 'tf.choiceResults.title', default: 'Detail')}" />
        <title><g:message code="tf.title" args="[page]" /></title>
        <g:javascript src="swfobject.js"/>
        <link rel="stylesheet" href="${resource(dir:'css/smoothness',file:'jquery-ui-1.8.custom.css')}" />
        <jq:plugin name="jquery-ui"/>
        
        <script type="text/javascript">
        $(function() {
            // fixing <p> probelm
            $("a.reference > p:first-child").css("margin-top", "0px");
			$("a.reference > p:last-child").css("margin-bottom", "0px");
				
        	var $dailog = $("div[id^='fullReview_']").dialog({
    			width: 640, 
    			height: 300,
    			autoOpen: false,
    			modal: true,
    			buttons: {"Close":function(){$(this).dialog("close");}}
    		});
    	});

    	function showFullReview(id) {
        	$('#' + id).dialog("open");
    	}
        </script>
    </head>
    <content tag="body">
	    <div id="content">
			  <g:if test="${cookie(name:TechniqueController.BACK_COOKIE_NAME)}">
	          <span class="nav_buttons">
	              <ul class="left">
	              	<g:if test="${cookie(name:TechniqueController.BACK_COOKIE_NAME) == 'listAll'}">
	              		<g:set var="backButton" value="button_backAllTech"/>
	              	</g:if>
	              	<g:else>
	              		<g:set var="backButton" value="button_backPosTech"/>
	              	</g:else>
	              	<li id="${backButton}"><a href="${createLink()}/${cookie(name:TechniqueController.BACK_COOKIE_NAME)}"></a></li>
	              </ul>
	          </span>
	          </g:if>
	          
	          <span class="nav_buttons">
	              <ul class="right">
	                <li id="button_backStart"><g:link controller="homePage"/></li>
	              </ul>
	          </span>
	           
	        <div class="clear"></div>
	        
	        <g:if test="${flash.message}">
	        	<div class="message">${flash.message}</div>
        	</g:if>
        	
	        <g:else>   
		        <h1><g:fieldValue bean="${technique}" field="name"/></h1>
		
		        <h5>About this technique</h5>
		        <hr>
					${technique.description}
					
				<g:if test="${technique.caseStudies.size()!=0}">
				<h5>Case studies</h5>
				<hr>
				<ul>
					<g:each var="caseStudy" 
						in="${technique.caseStudies}">
						<li><a href="${caseStudy.url}">${caseStudy.name}</a></li>
					</g:each>
				</ul>
				</g:if>
				
				<g:if test="${technique.reviews.size()!=0}">
				<h5>References</h5>
				<hr>
				<ul>
					<g:each var="review" 
						in="${technique.reviews}">
						<li>
                                            <g:if test="${review.referenceNames != null && review.referenceNames.length() != 0}">
						${review.referenceNames.encodeAsHTML()},&nbsp;
                                            </g:if>
                        <g:if test="${review.url}">
							<a class="reference" href="${review.url}" target="_blank">${review.title}</a></li>
                        </g:if>
                        <g:else>
							<div id="fullReview_${review.id}" style="display: none;">${review.fullReference}</div>
							<a class="reference" href="javascript:showFullReview('fullReview_${review.id}')">${review.title}</a></li>
                        </g:else>
					</g:each>
				</ul>
				</g:if>
				
                        <g:if test="${outputMedia != null && outputMedia.size() != 0}">
                        <div>
		        <h5>Output examples</h5>
		        <hr>
		
                                <g:each in="${outputMedia}" var="medium">
                                <div style="width:250px;display:inline-block;overflow:auto;vertical-align:top">
	                                <div style="width:225px;">
	                                   <g:render template="/media/show"
	                                             model="${[medium:medium, format:'original', section:MediaSection.OUTPUT]}"/>
			                   		   <span class="caption"><p>${medium.caption}</p></span>
			                   		</div>
                                </div>
                                </g:each>
                        </div>
                        </g:if>

                        <g:if test="${instrumentMedia != null && instrumentMedia.size() != 0}">
                        <div>
		        <h5>Instrument examples</h5>
		        <hr>
		
                                <g:each in="${instrumentMedia}" var="medium">
                                <div style="width:250px;display:inline-block;overflow:auto;vertical-align:top">
                                	<div style="width:225px;">
	                                   <g:render template="/media/show"
	                                             model="${[medium:medium, format:'original', section:MediaSection.INSTRUMENT]}"/>
			                   		   <span class="caption"><p>${medium.caption}</p></span>
		                   			</div>
                                </div>
                                </g:each>

                        </div>
                        </g:if>

			</g:else>
	        <p><span style="margin: 10px 0 10px 0;"><a class="top" href="#top" title="top">TOP</a></span></p>
	
	    </div>        
    </content>
    <content tag="infobox">
	<g:if test="${technique.contacts != null && technique.contacts.size()!=0}">
		<table cellpadding="0" border="0" width="100%" style="padding-top: 12em;">
        <tbody><tr>
            <td>
            <h5>Contact an expert</h5>
        	<hr>
            
            <div align="center" class="content">
                    <h2 align="left" class="content">If you are interested in this technique please contact one of our experts at the most suitable location.</h2>
	      <g:each var="contact" in="${technique.contacts}">
              <p align="left"><strong>${contact.location.institution.encodeAsHTML()}</strong><br>
                        ${contact.title.encodeAsHTML()} ${contact.name.encodeAsHTML()}<br>

                        T: ${contact.telephone.encodeAsHTML()}<br>
                        E: <a href="mailto:${contact.email}">${contact.email}</a>
                        <br>
              </p>
	      </g:each>
            </div>
        	</td>
        	</tr>
    	</tbody></table> 
	</g:if>
    </content>

