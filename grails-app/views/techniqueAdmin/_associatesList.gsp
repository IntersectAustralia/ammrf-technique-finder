<div>
    <h3>${message(code: type + '.association.title', default: type)}</h3>
    <table id="new${type}List">
            <thead>
                <tr>
                        <th></th>
		    <g:each in="${fieldsInSelect}" var="field">
			<th>${message(code: type + '.' + field + '.label', default: field)}</th>
                    </g:each>
                </tr>
            </thead>
            <tbody>
            <g:each in="${associates}" var="instance">
                <tr id="new${type}_${instance.id}" class="ui-state-default">
                
                    <td><input type="checkbox" name="associate" value="${instance.id}"/></td>
		    <g:each in="${fieldsInSelect}" var="field">
		    	<g:set var="fieldExpr" value="${Eval.me('instance', instance, 'instance.' + ((fieldPaths != null && fieldPaths[field] != null) ? fieldPaths[field] : field))}"/>
		    	<td>${fieldExpr}</td>
		    </g:each>

                </tr>
            </g:each>
            </tbody>
   </table>
   <!-- not shown, used in addContact to "copy & paste" a row from here into main table -->
   <table id="${type}DB" style="display:none">
        <g:each in="${associates}" var="object">
	    <g:render template="associateRow" model="${[instance:object, fields:fields, fieldPaths:fieldPaths, type:type, fieldsInSort:fieldsInSort]}"/>
        </g:each>
   </table>
</div>
