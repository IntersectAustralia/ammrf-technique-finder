<g:form>
    <g:hiddenField name="id" value="${instance?.id}" />
    <g:render template="/templates/formButtons" model="${[actions:['edit', 'delete']]}"/>
</g:form>