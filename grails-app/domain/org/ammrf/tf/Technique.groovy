package org.ammrf.tf

/**
 * Technique
 * 
 * @author Andrey Chernyshov 
 */
class Technique {
	
	/** Name of the technique */
	String name

	/** Alternative names, hidden */
    String alternativeNames

	/** Keywords, hidden */
	String keywords

	/** Short description that should appear in the search result */
	String summary

	/** Long description */
	String description

    SortedSet<Contact> contacts
	
	SortedSet<Review> reviews
	
	SortedSet<CaseStudy> caseStudies
    
    static searchable = {
		except = ["version"]
		name boost:16.0
		alternativeNames boost:16.0
		summary boost:4.0
		description boost:4.0
		keywords boost:4.0
		contacts component:true
		caseStudies component:true
		reviews component:true
		options component:true
		medias component:true
	}

	static hasMany = [reviews:Review, caseStudies:CaseStudy, contacts:Contact, options:OptionCombination, medias:MediaInSection]
	                  
	static constraints = {
		name(blank:false, unique:true, maxSize:255)
		summary(blank:false, maxSize: 1024)
		description(blank:false, maxSize: 65536)
                keywords(maxSize:1024)
                alternativeNames(maxSize:1024)
	}
	
	static mapping = {
		columns {
			summary type:'text'
			description type:'text'
			alternativeNames type:'text'
			keywords type:'text'
		}
	}
	
	String toString() {
		name
	}
}
