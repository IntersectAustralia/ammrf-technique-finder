package org.ammrf.tf

import grails.test.*

class ReviewTests extends GrailsUnitTestCase {
	def review
	
    protected void setUp() {
        super.setUp()
        review = new Review(referenceNames:'Some Names', title:'Test review', fullReference:'reference', url:'www.test.com')
        mockForConstraintsTests(Review, [review])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testTitleBlank() {
    	review.title = ''
    	assertFalse 'Validation should have failed', review.validate()
    	assertEquals 'blank', review.errors.title
    }
    
    void testUrlNotEmpty() {
    	review.url = 'blah'
    	assertFalse 'Validation should have failed', review.validate()
    	assertEquals 'url', review.errors.url
    }
    
    void testUrlAndFullReferenceEmpty() {
    	review.url = ''
    	review.fullReference = ''
    	assertFalse 'Validation should have failed', review.validate()
    	assertEquals 'validator', review.errors.fullReference
    }

    void testFullReferenceProvided() {
    	review.url = ''
    	review.fullReference = 'Something'
    	assertTrue 'Validation should have passed', review.validate()
    }

    void testTitleLenght() {
    	Util.checkFieldLength this, review, 'title', 255
    }
    
    void testReferenceNamesLenght() {
    	Util.checkFieldLength this, review, 'referenceNames', 255
    }
    
    void testFullReferenceLenght() {
    	Util.checkFieldLength this, review, 'fullReference', 2048
    }
    
    void testUrlLength() {
    	Util.checkUrlLength this, review, 'url', 1024
    }

    void testToString() {
        assertTrue 'toString wrong', review.toString().contains(review.referenceNames) && review.toString().contains(review.title)
    }
    
	void testComparator() {
		def review2 = new Review(referenceNames:review.referenceNames, title:"$review.title 2", fullReference:review.fullReference, url:review.url)
		assertTrue 'Should use review.title for ordering', review.compareTo(review2) < 0
  }
}
