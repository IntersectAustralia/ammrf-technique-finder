<%@ page import="org.ammrf.tf.MediaSection"%>
<div>
    <h3>Section: ${section}</h3>
    <table id="newMediaList">
            <thead>
                <tr>
                        <th></th>
                	<th>${message(code: 'media.thumbnail.label', default: 'Thumbnail')}</th>
                	<th>${message(code: 'media.name.label', default: 'Name')}<br />
                	${message(code: 'media.dimensions.label', default: 'Dimensions')}</th>
                </tr>
            </thead>
            <tbody>
            <g:each in="${media}" var="medium">
                <tr id="newMedia_${medium.id}" class="ui-state-default">
                
                    <td><g:if test="${section == MediaSection.LIST}"><input type="radio" name="media" value="${medium.id}"/></g:if>
                        <g:else><input type="checkbox" name="media" value="${medium.id}"/></g:else></td>
					
					<td><g:render template="/media/show" model="${[medium:medium, format:'thumbnail', section:section]}"/></td>	
                
                    <td>
                        ${fieldValue(bean: medium, field: "name")} <br />
                        ${medium.original?.width}x${medium.original?.height}</td>

                </tr>
            </g:each>
            </tbody>
   </table>
   <!-- not shown, used in addMedia to "copy & paste" a row from here into main table
        probably this should be a template to use in here as well as in editMedia.gsp -->
   <table id="mediaDB" style="display:none">
                    <g:each in="${media}" var="medium">
			<g:render template="imageRow" model="${[medium:medium,section:section]}"/>
                    </g:each>
   </table>
</div>
