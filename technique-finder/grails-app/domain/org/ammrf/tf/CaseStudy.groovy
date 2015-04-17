package org.ammrf.tf

/**
 * Case study
 * 
 * @author Andrey Chernyshov
 *
 */
class CaseStudy implements Comparable<CaseStudy>{
	
	/** Display name */
    String name
    
    /** URL to the actual case study */
    String url
    
    static searchable = {
		except = ["version"]
	}
    
	static constraints = {
    	name(blank:false, maxSize:255)
    	url(blank:false, url:true, maxSize:1024)
    }
    
	public String toString() {
		return name;
	}
	
	public int compareTo(CaseStudy caseStudy) {
		return GspUtils.cleanString(name).compareTo(GspUtils.cleanString(caseStudy.name))
	}
}
