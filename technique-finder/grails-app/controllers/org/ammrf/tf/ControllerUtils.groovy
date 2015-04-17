package org.ammrf.tf

import javax.servlet.http.HttpServletResponse;

/**
 * Utility class used in controllers
 */
class ControllerUtils {
	private static final String HEADER_PRAGMA = "Pragma";
	private static final String HEADER_EXPIRES = "Expires";
	private static final String HEADER_CACHE_CONTROL = "Cache-Control";
	
	/**
	 * Disables browser caching for the provided response
	 */
	public static void disableBrowserCache(HttpServletResponse response) {
		response.setHeader(HEADER_PRAGMA, "no-cache");
	    response.setDateHeader(HEADER_EXPIRES, 1L);
	    response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
	    response.addHeader(HEADER_CACHE_CONTROL, "no-store");
	}
}
