<%@ page import="org.ammrf.tf.MediaType" %>
<%@ page import="org.ammrf.tf.MediaSection" %>
<g:if test="${format == 'original'}">
  <div style="display:block;margin-left:auto;margin-right:auto;">
  <g:if test="${medium.mediaType == MediaType.IMAGE}">
    <img id="media${section}_${medium.id}" src="${resource(base:grailsApplication.config.tf.mediafolder.baseUrl,file:medium?.original?.location)}"
      width="${section.width(medium?.original?.width, medium?.original?.height)}" 
      height="${section.height(medium?.original?.width, medium?.original?.height)}" alt="[${medium?.name}]">
  </g:if>
  <g:else>
    <div id="media${section}_${medium.id}">
        <script type="text/javascript">document.write('<p>You need <a href="http://www.adobe.com/go/getflashplayer">Flash Player</a> installed to play this media.</p>');</script>
        <noscript>You need Javascript enabled. Please consult you browser's manual to enable Javascript for this website.</noscript>
    </div>
      <g:render template="/media/showMovie" model="${[medium:medium, placeholderId:'media' + section + '_' + medium.id]}"/>
  </g:else>
  </div>
</g:if>
<g:else>
<g:if test="${format == 'admin-show'}">
  <g:if test="${medium.mediaType == MediaType.IMAGE}">
    <img id="media${section}_${medium.id}" src="${resource(base:grailsApplication.config.tf.mediafolder.baseUrl,file:medium?.original?.location)}"
      width="${medium?.original?.width}" height="${medium?.original?.height}" alt="[${medium?.name}]">
  </g:if>
  <g:else>
    <div id="media${section}_${medium.id}">
        <script type="text/javascript">document.write('<p>You need <a href="http://www.adobe.com/go/getflashplayer">Flash Player</a> installed to play this media.</p>');</script>
        <noscript>You need Javascript enabled. Please consult you browser's manual to enable Javascript for this website.</noscript>
    </div>
    <g:render template="/media/showMovie" model="${[medium:medium, placeholderId:'media' + section + '_' + medium.id]}"/>
  </g:else>
</g:if>
<g:else>
  <g:if test="${medium.mediaType == MediaType.IMAGE}">
    <img id="media${section}_${medium.id}" class="sortHandle" src="${resource(base:grailsApplication.config.tf.mediafolder.baseUrl,file:medium?.thumbnail?.location)}"
         width="${medium?.thumbnail?.width}" height="${medium?.thumbnail?.height}" alt="[${medium?.name}]">
  </g:if>
  <g:else>
    <img id="media${section}_${medium.id}" class="sortHandle" src="${resource(base:grailsApplication.config.tf.mediafolder.baseUrl,file:medium?.thumbnail?.location)}"
         width="100" height="75" alt="[${medium?.name}]">
  </g:else>
</g:else>
</g:else>
