package org.ammrf.tf;

import java.util.regex.Pattern;

/**
 * Utilities to use in the gsp pages
 * 
 * @author Andrey Chernyshov
 *
 */
public class GspUtils {
	/** Pattern to find html tags */
	private static Pattern htmlMarkup = Pattern.compile("<[^>]+>");
	private static Pattern whiteSpace = Pattern.compile("\\s+");
	
	/**
	 * Removes html markup (tags)
	 * 
	 * @param html string to search and remove in
	 * @return string with all html tags removed
	 */
	public static String removeHtmlMarkup(String html) {
		return htmlMarkup.matcher(html).replaceAll("");
	}

	public static String cleanString(String html) {
		return whiteSpace.matcher(removeHtmlMarkup(html)).replaceAll(" ").trim();
	}
}
