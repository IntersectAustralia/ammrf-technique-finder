<%@page import="org.ammrf.tf.Science"%>
<%@page import="org.ammrf.tf.MediaSection"%>
<%@ page import="org.ammrf.tf.GspUtils"%>

    <head>
        <meta name="layout" content="public" />
        <g:set var="page" value="${message(code: 'tf.choiceResults.title', default: 'Results')}" />
        <title><g:message code="tf.title" args="[page]" /></title>
        <g:javascript src="swfobject.js"/>
        <script type="text/javascript">
			$(function() {
				$("div.summary > p:first-child").css("margin-top", "0px");
				$("div.summary > p:last-child").css("margin-bottom", "0px");
			});
			
		</script>
    </head>
    <content tag="body">

    <div id="content">
			
		  <g:if test="${leftOption}">
          <span class="nav_buttons">
              <ul class="left">
              	<g:if test="${leftOption?.science == Science.BIOLOGY}">
              		<li id="button_backBio"><g:link controller="optionsSelection" action="listBioOptions"/></li>
              	</g:if>
              	<g:else>
              		<li id="button_backPhys"><g:link controller="optionsSelection" action="listPhysicsOptions"/></li>
              	</g:else>
              </ul>
          </span>
          </g:if>
          
          <span class="nav_buttons">
              <ul class="right">
                <li id="button_backStart"><g:link controller="homePage"/></li>
              </ul>
          </span>
           
        <div class="clear"></div>
        
           
        <h1>Possible Techniques</h1>

        <g:if test="${search}">
        	<h5>Search by keyword</h5>
			<hr>
			<table width="100%">
			    <tr>
			        <td class="yellow_box" style="padding:1em;" valign="top"><p>
			        	${searchExplanation}</p>
			        	<g:form name="searchForm" controller="technique" action="search" method="GET">
			            Search <input id="searchBox" name="q" type="text" style="width:85%;" value="${resultsTitle?.encodeAsHTML()}">
			            <p><span class="go_container"><span id="button_go"><a href="javascript:document.searchForm.submit()"></a></span></p>
			            </g:form>
			         </td>
			    </tr>
			</table>
        </g:if>

        <g:if test="${flash.message}">
        	<div class="message">${flash.message}</div>
        	<div class="clear"/>
        </g:if>
        <g:else>
        	<p>Results for: <span class="orange"><b>${GspUtils.removeHtmlMarkup(resultsTitle)}</b></span></p>
        	
	        <g:if test="${techniques}">
	        
	         <g:each var="technique" in="${techniques}" status="i">
		        <h5>${technique.name.encodeAsHTML()}</h5>
		        <hr>
		
		        <table width="100%">
		            <tr>
		                <td valign="top">
		                    <div class="summary">${technique.summary}</div>
		        
		                    <g:link action="show" id="${technique.id}" elementId="Technique_${technique.id}">Detailed information and contact details</g:link></td>
		                <td width="10"><img src="${resource(dir:'images/ammrf',file:'space.gif')}" width="10" height="5" /></td>
		                <td width="140" valign="top">
	                           <g:if test="${techniqueMedia.get(i) != null}">
	                           <g:render template="/media/show" model="${[medium:techniqueMedia.get(i), format:'original', section:MediaSection.LIST]}"/>
	                           </g:if>
	                        </td>
		            </tr>
		        </table>
		        
	         </g:each>
	        
	        <g:if test="${searchResult && (Math.ceil(searchResult.total / searchResult.max)) > 1}">
	        	<br /><br />
		        <div class="paging">
		              Page:
		              <g:paginate action="search" params="[q: params.q]" total="${searchResult.total}" prev="&lt; previous" next="next &gt;"/>
		        </div>
	        </g:if>
	        
	        </g:if>
	        <g:else>
	        	<g:message code="tf.techniques.notFound" default="Nothing found"/>
	        </g:else>
        </g:else>
        
        

        <p><span style="margin: 10px 0 10px 0;"><a class="top" href="#top" title="top">TOP</a></span></p>

    </div>        
    </content>

