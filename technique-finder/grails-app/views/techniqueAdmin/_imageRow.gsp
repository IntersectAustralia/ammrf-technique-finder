<tr id="media_${medium.id}" class="ui-state-default"  style="width:594px;">

    <td><g:render template="/media/show" model="${[medium:medium, format:'thumbnail', section:section]}"/></td>

    <td>
        ${fieldValue(bean: medium, field: "name")} <br />
        ${medium.original?.width}x${medium.original?.height}</td>

    <td>${fieldValue(bean: medium, field: "caption")}</td>

    <td><img style="float:right;" src="${resource(dir:'images/skin',file:'database_delete.png')}" alt="Remove" title="Remove" onclick="removeMedia(${medium.id},'${section}')"/></td>

</tr>

