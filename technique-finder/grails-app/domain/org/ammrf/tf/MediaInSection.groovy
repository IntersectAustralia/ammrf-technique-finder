package org.ammrf.tf

import java.io.Serializable;


/**
 * Media-Technique relationship 
 * @author Carlos Aya
 */
class MediaInSection {

        /** the technique */
        Technique technique
	
	/** The media */
	Media media
	
	/** Which section */
	MediaSection section
	
	/** 1-based priority of this media */
	int priority
	
	static constraints = {
		priority(min:1)
        }
        
    static searchable = {
		only = ["media"]
		media component:true
	}
}
