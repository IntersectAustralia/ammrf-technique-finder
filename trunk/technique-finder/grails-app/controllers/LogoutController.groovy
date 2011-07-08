import org.ammrf.tf.ControllerUtils;

/**
 * Logout Controller (Example).
 */
class LogoutController {

	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index = {
		log.debug "Setting cache headers to clear browser cache"
		ControllerUtils.disableBrowserCache(response)
		
		redirect(uri: '/j_spring_security_logout')
	}
}
