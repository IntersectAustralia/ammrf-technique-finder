package org.ammrf.tf

/**
 * Options on the options select page.
 * 
 * @author Andrey Chernyshov
 *
 */
class Option {
	/** Option display name */
	String name
	
	/** Science (Bio/Physics) this option is for */
	Science science
	
	/** Type of the option (left/right) */
	OptionType type
	
	/** 1-based priority of the option (like index in the list) */
	int priority
	
	static searchable = {
		only = ["name"]
	}
	
    static constraints = {
		name(blank:false, maxSize:255)
		priority(min:1)
    }
	
	static mapping = {
		table 'option_choice'
	}
	
	String toString() {
		name
	}
}
