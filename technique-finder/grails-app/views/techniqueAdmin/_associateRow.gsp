<tr id="${type}_${instance.id}" class="ui-state-default" theId="${instance.id}" style="width:400px;">

    <input type="hidden" name="${type}" value="${instance.id}" />

    <g:each in="${fields}" var="field">
    <g:set var="fieldExpr" value="${Eval.me('instance', instance, 'instance.' + ((fieldPaths != null && fieldPaths[field] != null) ? fieldPaths[field] : field))}"/>
    <td>${fieldExpr}</td>
    </g:each>

    <td><img style="float:right;" src="${resource(dir:'images/skin',file:'database_delete.png')}" alt="Remove" title="Remove" onclick="removeAssociate('${type}', ${instance.id})"/></td>

</tr>
<script type="text/javascript">
// -- put as data the array of sort fields in 'keys' using jQuery
// -- lazyness here, to avoid logic for the ',' a dummy last value is inserted
$(function(){
	var $row = $('#${type}_${instance.id}');
	var keys = [
	<g:each in="${fieldsInSort}" var="field">
    		<g:set var="fieldExpr" value="${Eval.me('instance', instance, 'instance.' + ((fieldPaths != null && fieldPaths[field] != null) ? fieldPaths[field] : field))}"/>
	        <g:valueToJavaScript value="${fieldExpr.toString()}" />,
	</g:each>
		'-'
	];
	$row.data('keys', keys);
})
</script>
