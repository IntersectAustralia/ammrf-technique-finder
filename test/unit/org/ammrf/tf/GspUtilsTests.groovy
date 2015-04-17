package org.ammrf.tf

import grails.test.*

class GspUtilsTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testRemoveHtmlMarkup() {
    	assertEquals "Not all html tags where removed", "some bold", GspUtils.removeHtmlMarkup("some <b>bold</b>")
    }
}
