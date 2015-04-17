package org.ammrf.tf;

/**
 * Status (type) of the AMMRF Node.
 * Currently has only two options "Node" or "Linked Lab"
 * 
 * @author Andrey Chernyshov
 *
 */
public enum LocationStatus {
	ND("Node"), LL("Linked Lab");
	
	private String name;
	
	private LocationStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}