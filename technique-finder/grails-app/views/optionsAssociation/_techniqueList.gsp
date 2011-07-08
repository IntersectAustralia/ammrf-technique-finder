<jq:plugin name="jquery-ui"/>

<style type="text/css">
	#allTechniques { list-style-type: none;}
</style>

<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
	<div class="clear"/>
</g:if>

<g:if test="${techniques?.size > 0}">
    <ul id="allTechniques">
    	<g:each in="${techniques}" status="i" var="technique">
            <li><input type="checkbox" name="technique" value="${technique.id?.encodeAsHTML()}" style="margin-right:15px;"/><label>${technique.name?.encodeAsHTML()}</label></li>
        </g:each>
    </ul>
</g:if>
<g:else>
	<g:message code="tf.techniques.notFound" default="Nothing found"/>
</g:else>