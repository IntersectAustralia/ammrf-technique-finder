package org.ammrf.tf

import java.io.Serializable;


/**
 * Option choices combination.
 * Used to build relationship between technique and option choice pair (left/right columns).
 * 
 * @author Andrey Chernyshov
 */
class OptionCombination {
	
	/** Left column on the selection screen */
	Option left
	
	/** Right column on the selection screen */
	Option right
	
	/** Technique by which this option combination is covered */
	Technique technique
	
	/** 1-based priority of the combination (like index in the list) */
	int priority
	
	static searchable = {
		boost 16.0
		only = ["left", "right"]
		left component:true
		right component:true
	}
	
	static constraints = {
		priority(min:1)
		left(validator:{val, obj -> 
			 	val.science == obj.right.science && 
			 	val.type == OptionType.LEFT && 
			 	obj.right.type == OptionType.RIGHT
			 }
		)
    }
	
}
