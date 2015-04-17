package org.ammrf.tf

/**
 * Static but editable (through admin interface) content on the pages.
 * 
 * @author Andrey Chernyshov
 */
class StaticContent {
	/** Content name/key */
	String name
	
	/** Content itself */
	String text // 64Kb Unicode text with HTML/Wiki Markup
	
   static constraints = {
		name(blank:false, unique:true)
		text(maxSize: 65536)
    }
	
	static mapping = {
		columns {
			text type:'text'
		}
	}
	
}
