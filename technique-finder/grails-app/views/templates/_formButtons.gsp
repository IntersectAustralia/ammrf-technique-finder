<g:if test="${actions?.contains('create')}"><span class="buttons"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span></g:if>
<g:if test="${actions?.contains('edit')}"><span class="buttons"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span></g:if>
<g:if test="${actions?.contains('update')}"><span class="buttons"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span></g:if>
<g:if test="${actions?.contains('delete')}"><span class="buttons"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span></g:if>
<g:set var="listLink" value="${createLink(action:'list',fragment:instance?.id)}" />
<g:if test="${actions?.contains('cancel')}"><span class="buttons"><input type="submit" class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" onclick="window.location.href='${listLink}'; return false;" /></span></g:if>