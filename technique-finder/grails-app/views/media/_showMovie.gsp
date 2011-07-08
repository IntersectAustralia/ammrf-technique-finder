<g:flashPlayer id="${placeholderId}" player="${resource(dir:'swf', file:'player_flv_maxi.swf')}" 
	varFlv="${resource(base:grailsApplication.config.tf.mediafolder.baseUrl, file: medium?.original?.location)}" 
 	varStartimage="${resource(base:grailsApplication.config.tf.mediafolder.baseUrl, file: medium?.thumbnail?.location)}"
    width="${medium?.original?.width}" height="${20 + medium?.original?.height}"
	paramAllowfullscreen="true" varShowiconplay="1"
    varShowstop="1" varShowplayer="always" varShowtime="1" varShowfullscreen="1" 
    varMargin="0" varVolume="0" varBuffershowbg="0" varBuffermessage=""
   />