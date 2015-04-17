package org.ammrf.tf

/**
 * ImageFile
 * 
 * @author Carlos Aya
 */
class MediaFile {

	/** file path (relative to media library) */
	String location

	/** mime type */
	String mime

	/** width of media */
	int width

	/** height of media */
	int height

	/** total size in bytes */
        long size

	static belongsTo = Media

        static constraints = {
		width(min:1)
		height(min:1)
		size(min:1L)
        }

}

