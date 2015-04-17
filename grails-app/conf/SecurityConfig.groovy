security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true
	
	// user caching
	cacheUsers = false
	
	loginUserDomainClass = "org.ammrf.tf.security.User"
	authorityDomainClass = "org.ammrf.tf.security.Role"
	
	useRequestMapDomainClass = false
	useControllerAnnotations = true
	
	/** rememberMeServices */
	cookieName = 'ammrf_technique_finder_remember_me'
	rememberMeKey = 'adminRemember'
	
	controllerAnnotationStaticRules = ['/searchable/**':['ROLE_SUPER'], '/monitoring/**':['ROLE_SUPER']]
}
