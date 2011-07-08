                            <tr class="prop _${type}">
                                <td valign="top" class="name">
                                    <label for="${type}">${message(code:"technique."+type+".label", default:type)}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: instance, field: type, 'errors')}">
					<table id="${type}List" style="width:770;">
					    <thead>
						<tr>
						    <g:each in="${fields}" var="field">
							<th>${message(code: type + '.' + field + '.label', default: field)}</th>
                                                    </g:each>
							<th>${message(code: 'actions.label', default: 'Actions')}</th>
						</tr>
					    </thead>
					    <tbody class="sortedContent">
                                            <g:if test="${objects != null && objects.size() != 0}">
					        <g:each in="${objects}" var="object">
						    <g:render template="associateRow"
							model="${[instance:object, fields:fields, fieldPaths:fieldPaths, type:type, fieldsInSort:fieldsInSort]}"/>
					        </g:each>
                                            </g:if>
					    </tbody>
					</table>
			               <ul class="action_links">
			                   <li><a id="add${type}Button" href="#" onclick="${'listAssociates(\''+type+'\', \''+createLink(action:'listAssociates') + '\')'}"
                                              ><span class="add"></span>${message(code: type + '.add.label', default: 'Add')}<div></a></li>
			               </ul>
                                </td>
                            </tr>

