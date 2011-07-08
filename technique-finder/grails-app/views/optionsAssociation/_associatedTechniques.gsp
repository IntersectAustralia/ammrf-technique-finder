<jq:plugin name="jquery-ui"/>

<script type="text/javascript">
	$(function() {
		$("#associatedTechniques").sortable({ 
			axis:'y',
			containment:'parent',
			update: function(event, ui) {disableOptionsSelection(true);} 
		});
		
		var $dailog = $("#techniquesDialog").dialog({
			width: 640, 
			height: 480,
			autoOpen: false,
			modal: true,
			buttons: {"Add":function(){$(this).dialog("close"); addTechniques(); $(this).empty();}, "Cancel":function(){$(this).empty().dialog("close");}}
		});

		var $transparentMsg = $("#transparentMsg")
		if($transparentMsg) {
			humanMsg.displayMsg($transparentMsg.text());
		}
	});
	
	function addTechniques() {
		var $checkedTechniques = $("input[name=technique]:checked");
		if($checkedTechniques.length > 0) {
			$("#notFoundMessage").remove();
			disableOptionsSelection(true);
		}
		$checkedTechniques.each(function(){
			$('<li id="technique_' + this.value 
					+ '" class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>' 
					+ $(this).next().text() 
					+ '<a href="#" style="float:right;" onclick="removeTechnique('
					+ this.value + ')">'
					+ '<img border="0" src="${resource(dir:'images/skin',file:'database_delete.png')}" alt="Remove" title="Remove"/>'
					+ '</a></li>').insertBefore("#associatedTechniquesEnd")
		});
	}

	function saveTechniques() {
		var techniqueIds = $("#associatedTechniques").sortable('serialize', {key:'techniqueIds'});
		var leftOption = $("#leftOptionVal").val();
		var rightOption = $("#rightOptionVal").val();
		$.ajax({
			   type: "POST",
			   url: "update",
			   data: "leftOption=" + leftOption +"&rightOption=" + rightOption + "&" + techniqueIds,
			   success: function(data){
		    		$("#techniqueList").html(data);
		    		disableOptionsSelection(false);
		  		}
		});	
	}

	function listTechniques() {
		var techniqueIds = $("#associatedTechniques").sortable('serialize', {key:'techniqueIds'});
		$.ajax({
			   type: "POST",
			   url: "listTechniques",
			   data: techniqueIds,
			   success: function(data){
		    		$("#techniquesDialog").html(data).dialog('open');
		  		}
		});
	}

	function cancelEdit() {
		disableOptionsSelection(false);
		$('#showTechniques').click();
	}

	function removeTechnique(id) {
		$("#technique_"+id).remove();
		disableOptionsSelection(true);
	}

</script>
	
<g:if test="${flash.message}">
	<g:message code="tf.options.association.updated.message" default="${flash.message}" transparent="true"/>
	${flash.clear()}
</g:if>


<div>
	<h3>Step 3: Add/remove/order associated techniques</h3><br/>
	<ul id="associatedTechniques" class="associatedTechniques">
		<br/>
		<g:if test="${techniques?.size > 0}">
		    	<g:each in="${techniques}" status="i" var="technique">
		            <li id="technique_${technique.id?.encodeAsHTML()}" class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>${technique.name?.encodeAsHTML()}<a href="#" style="float:right;" onclick="removeTechnique(${technique.id?.encodeAsHTML()})"><img border="0" src="${resource(dir:'images/skin',file:'database_delete.png')}" alt="Remove" title="Remove"/></a></li>
		        </g:each>
		</g:if>
		<g:else>
			<div id="notFoundMessage"><g:message code="tf.techniques.notFound" default="Nothing found"/></div>
		</g:else>
		<br id="associatedTechniquesEnd"/>
	</ul>
</div>

<ul class="action_links">
	<li><a id="addTechniqueButton" href="#" onclick="listTechniques()"><span class="add"></span>Add technique</a></li>
	<li><a id="saveTechniquesButton" href="#" onclick="saveTechniques()"><span class="save"></span>Save</a></li>
	<li><a id="cancelButton" href="#" onclick="cancelEdit()"><span class="delete"></span>Cancel</a></li>
</ul>

<div id="techniquesDialog" title="Available Techniques"></div>