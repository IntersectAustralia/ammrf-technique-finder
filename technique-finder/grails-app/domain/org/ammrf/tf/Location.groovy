package org.ammrf.tf

/**
 * Describes AMMRF node (fro example USyd, ANU, etc.)
 * 
 * @author Andrey Chernyshov
 */
class Location {

	/** Name of institution (this is displayed on the public site) */
	String institution

	/** Internal name */
	String centerName

	/** Address */
	String address

	/** One of the AU states  */
	State state

	/** Node or linked lab */
	LocationStatus status

	/** 1-based priority of the location (like index in the list) */
	int priority
	
	static searchable = {
		except = ["version", "status", "priority"]
	}
	
    static constraints = {
		institution(blank:false, unique:true, maxSize:255)
		centerName(maxSize:255)
		priority(min:1)
    }
	
	static mapping = {
		columns {
			address type:'text'
		}
	}
}
