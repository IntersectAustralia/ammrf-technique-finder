
    <head>
        <meta name="layout" content="public" />
        <g:set var="page" value="${message(code: 'tf.biologyChoices.title', default: 'Choices')}" />
        <title><g:message code="tf.title" args="[page]" /></title>
        
        <g:javascript src="options-selection.js"/>
        <script type="text/javascript">
		<!--

		// sets new option value into hidden input field
		// enables show button if two options have been selected
		function optionChanged(optionName, value) {
			$('#' + optionName + 'Val').val(value)
			if($('#button_showPosTech').is(':hidden')) {
				if($('#leftOptionVal').val() != '' && $('#rightOptionVal').val() != '') {
					$('#disabled_showPosTech').hide()
					$('#button_showPosTech').show()
				}
			}
		}
			
		//-->
		
		</script>
		<jq:jquery>
			// restore options selection if page was refreshed
			restoreOptionSelection('leftOption')
			restoreOptionSelection('rightOption')
		</jq:jquery>
    </head>
    <content tag="body">

	    <div id="content">
	          
	          <span class="nav_buttons">
	              <ul class="right">
	                <li id="button_backStart"><g:link controller="homePage"/></li>
	              </ul>
	          </span>
	           
	        <div class="clear"></div>
	           
	        ${quickGuide}
	        
	        <table width="100%">
	            <tr>
	                <g:render template="/templates/optionsSelection" model="${model}"/>
	            </tr>
	
	        </table>
	        
	        <div class="clear"></div>
		
			<br />
			
	        <span class="nav_buttons">
	        	<g:form method="get" controller="technique" action="list" name="choiceForm">
	              <ul class="right">
	              	<input type="hidden" id="leftOptionVal" name="leftOption"/>
	              	<input type="hidden" id="rightOptionVal" name="rightOption"/>
	                <li id="button_showPosTech" style="display: none;"><a href="javascript:document.choiceForm.submit()"></a></li>
	                <div class="right" id="disabled_showPosTech">
	              </ul>
	            </g:form>  
	        </span>
	        
	        <br />
	        
	        <p><span style="margin: 10px 0 10px 0;"><a class="top" href="#top" title="top">TOP</a></span></p>
	
	    </div>        
    </content>

