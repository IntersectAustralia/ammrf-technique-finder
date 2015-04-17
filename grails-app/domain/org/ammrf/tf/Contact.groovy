package org.ammrf.tf

/**
 * Contact for the technique
 * 
 * @author Andrey Chernyshov
 *
 */
class Contact implements Comparable {
	
	/** Title (Dr, Mr, Prof, etc.) */
	String title
	
	/** Full name */
	String name
	
	/** Position in the organization (AMMRF/institution) */
	String position

	/** Contact phone */
	String telephone
	
	/** Contact email */
	String email
	
	/** Whether the person can be contacted for techniques or not */
	boolean techniqueContact
	
	/** AMMRF node */
	Location location
	
	static searchable = {
		except = ["version", "title", "techniqueContact"]
		location component:true
	}

    static constraints = {
		title(maxSize:25)
		name(blank:false, maxSize:255)
		telephone(maxSize:25,validator:{val, obj -> obj.email || obj.telephone})
		email(email:true, maxSize:255,validator:{val, obj -> obj.email || obj.telephone})
		position(maxSize:255)
    }
	
	static mapping = {
		position column: 'contact_position'
	}

        public int compareTo(obj) { 
		int val = location?.priority - obj?.location?.priority
                if (val == 0) {
                   return name?.compareTo(obj?.name)
                } else {
                   return val
                }
        }
}
