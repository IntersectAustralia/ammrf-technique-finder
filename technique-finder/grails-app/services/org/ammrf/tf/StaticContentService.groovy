package org.ammrf.tf

class StaticContentService {

    boolean transactional = true

    def findText(String name) {
    	StaticContent.findByName(name)?.text
    }
}
