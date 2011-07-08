package org.ammrf.tf

/**
 * Media
 * 
 * @author Carlos Aya
 */
class Media {

	/** caption rich text */
	String caption

	/** name from the upload form */
	String name

	/** type of media */
	MediaType mediaType

	/** original file */
	MediaFile original

	/** thumbnail version */
	MediaFile thumbnail

	static constraints = {
		caption(maxSize:500)
		original(nullable:false)
		thumbnail(nullable:false)
        }
	
	static searchable = {
		only = ["caption"]
	}

	public int compareTo(other) {
		return GspUtils.cleanString(name).compareTo(GspUtils.cleanString(other.name))
	}

}
	

