<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="page" value="${message(code: 'tf.optionsAssociation.title', default: 'Options association')}" />
        <title><g:message code="tf.title" args="[page]" /></title>
        
        <g:javascript src="options-selection.js"/>
        <script type="text/javascript">
		<!--
		var optionsDisabled = false;
		
		// sets new value of the option into corresponding javascript var
		// sends AJAX call to populate techniques for the selected options
		function optionChanged(optionName, value) {
			$('#' + optionName + 'Val').val(value)
			if($('#leftOptionVal').val() != '' && $('#rightOptionVal').val() != '') {
				$('#showTechniques').click();
				//$('#choiceForm').submit();
			}
		}

		function isOptionsDisabled() {
			return optionsDisabled;
		}

		// enables/disables options selection
		function disableOptionsSelection(disable) {
			optionsDisabled = disable;
			var $options = $("li[id^='leftOption']").add("li[id^='rightOption']").not($("li[style*='tf_bullet_active.gif']"));
				$options.each(function() {
					if(disable) {
						$(this).css("color", '#808080');
					} else {
						$(this).css("color", '');
					}
				});
		}
		
		//-->
		
		</script>
		<jq:jquery>
			// restore options selection if page was refreshed
			restoreOptionSelection('leftOption')
			restoreOptionSelection('rightOption')
		</jq:jquery>
    </head>
    <body>
    	<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/admin')}"><g:message code="tf.admin.home"/></a></span>
            <span id="spinner" class="menuButton" style="display:none; float:right;">
				<img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner"/>
			</span>
        </div>
	    <div class="body">
	    	<h1><g:message code="tf.option.association.${science}"/></h1>
	    	<div style="float:left;">
	        <table class="optionChoices">
	            <tr>
	                <g:render template="/templates/optionsSelection" model="${model}"/>
	            </tr>
	        </table>
	        </div>
	        
	        <div id="techniqueList" style="float:right; padding-left: 5em;">
	        </div>
        	
        	<g:formRemote id="choiceForm"
        				  url="[action:'listAssociatedTechniques']" 
        				  name="choiceForm" update="techniqueList" method="GET">
              	<input type="hidden" id="leftOptionVal" name="leftOption"/>
              	<input type="hidden" id="rightOptionVal" name="rightOption"/>
              	<input id="showTechniques" type="submit" value="Show Techniques" style="display:none;"/>
            </g:formRemote>  
	    </div>        
    </body>
</html>
