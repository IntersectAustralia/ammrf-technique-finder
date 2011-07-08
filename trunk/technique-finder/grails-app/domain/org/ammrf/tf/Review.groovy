package org.ammrf.tf

/**
 * Technique review or paper
 * 
 * @author Andrey Chernyshov
 *
 */
class Review implements Comparable<Review> {
	/** Authors, e.g. Whiting et al, (2008) */
	String referenceNames
	
	/** Title of the reference */
	String title
	
	/** Full reference text */
	String fullReference
	
	/** URL to the review text*/
	String url
	
	static searchable = {
		except = ["version"]
	}
	
    static constraints = {
		title(blank:false, maxSize:255)
		url(url:true, maxSize:1024)
		referenceNames(maxSize:255)
		fullReference(maxSize:2048, validator:{val, obj ->
			obj.url.size() > 0 || val.size() > 0
		})
    }
	
	public String toString() {
		return referenceNames + "-" + title;
	}
	
	public int compareTo(review) {
		def resp1 = GspUtils.cleanString(title).compareTo(GspUtils.cleanString(review.title))
		if (resp1 != 0) return resp1;
		return GspUtils.cleanString(referenceNames).compareTo(GspUtils.cleanString(review.referenceNames))
	}
}
