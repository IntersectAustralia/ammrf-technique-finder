<%@ page import="org.ammrf.tf.Science" %>

    <head>
        <meta name="layout" content="public" />
        <g:set var="page" value="${message(code: 'tf.homePage.title', default: 'Home')}" />
        <title><g:message code="tf.title" args="[page]" /></title>
    </head>
    <content tag="body">

        <div id="content">
	        ${quickGuide}
	        <h5>Option 1: Choose your research interest</h5>
			<hr>
			<table width="100%">
			    <tr>
			        <td class="yellow_box" style="padding:0.5em;" valign="top">
			            ${optionsExplanation}
			          <span class="buttons">
			              <ul>
			              	<li id="button_bio"><g:link controller="optionsSelection" action="listBioOptions"/></li>
			                <li id="button_phys"><g:link controller="optionsSelection" action="listPhysicsOptions"/></li>
			              </ul>
			          </span>
			          
			         </td>
			    </tr>
			</table>
			
			<h5>Option 2: Search by keyword</h5>
			<hr>
			<table width="100%">
			    <tr>
			        <td class="yellow_box" style="padding:1em;" valign="top">
			        	${searchExplanation}
			        	<g:form name="searchForm" controller="technique" action="search" method="GET">
			            Search <input id="searchBox" name="q" type="text" style="width:85%;">
			            <p><span class="go_container"><span id="button_go"><a href="javascript:document.searchForm.submit()"></a></span></span></p>
			            </g:form>
			         </td>
			    </tr>
			</table>
			
			<h5>Option 3: View list of available techniques</h5>
			<hr>
			<table width="100%">
			    <tr>
			        <td class="yellow_box" style="padding:1em;" valign="top">
			        	${allTechniquesExplanation}
			            <p><span class="go_container"><span id="button_viewList"><g:link controller="technique" action="listAll"/></span></span></p>
			         </td>
			    </tr>
			</table>
			<p><span style="margin: 10px 0 10px 0;"><a class="top" href="#top" title="top">TOP</a></span></p>
		</div>
    </content>
	
	<content tag="infobox">
		${infoboxContent}
	</content>
