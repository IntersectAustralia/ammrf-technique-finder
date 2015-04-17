<g:if test="${actions?.contains('view')}">
<td>
<ul class="action_links">
	<li><g:link action="show" id="${beanInstance?.id}"><span class="view"></span>View</g:link></li>
</ul>
</td>
</g:if> 

<g:if test="${actions?.contains('edit')}">
<td>
<ul class="action_links">
	<li><g:link action="edit" id="${beanInstance?.id}"><span class="edit"></span>Edit</g:link></li>
</ul>
</td>
</g:if>

<g:if test="${actions?.contains('delete')}">
<td>
<ul class="action_links">
	<li><g:link action="delete" id="${beanInstance?.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><span class="delete"></span>Delete</g:link></li>
</ul>
</td>
</g:if>

<g:if test="${actions?.contains('move')}">
<td>
<div class="move_container">
	<g:if test="${loopStatus > 0}">
   		<g:link action="moveUp" id="${beanInstance.id}" title="Move Up"><div class="up_active"></div></g:link>
   	</g:if>
   	<g:else>
   		<div class="up_inactive"></div>
   	</g:else>
   	<g:if test="${loopStatus < listSize - 1}">
    	<g:link action="moveDown" id="${beanInstance.id}" title="Move Down"><div class="down_active"></div></g:link> 
   	</g:if>
   	<g:else>
   		<div class="down_inactive"></div>
   	</g:else>
</div>
</td>
</g:if>