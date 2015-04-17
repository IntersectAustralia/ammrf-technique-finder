<g:if test="${instance?.id}">
	<g:render template="/templates/formButtons" model="${[actions:['update', 'delete', 'cancel']]}"/>
</g:if>
<g:else>
	<g:render template="/templates/formButtons" model="${[actions:['create', 'cancel']]}"/>
</g:else>
