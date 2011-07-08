package org.ammrf.tf.admin

import org.ammrf.tf.Review;


import grails.test.*

class ReviewControllerTests extends ControllerUnitTestCase {
	def reviews
	
    protected void setUp() {
        super.setUp()
        
        reviews = []
        for(i in [2, 1, 3]) {
        	reviews += new Review(referenceNames:"Test $i", title:"Test review $i", fullReference:'reference', url:'www.test.com')
        }
        mockDomain(Review, reviews)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListSorting() {
    	def model = controller.list()
    	assertEquals 'List sorting is incorrect', reviews.sort{a, b -> a.title.compareToIgnoreCase(b.title)}, model.reviewInstanceList
    }
    
    void testListMaxParamSmall() {
    	mockParams.max = 2
    	def model = controller.list()
    	assertEquals 'List size is incorrect', reviews.size(), model.reviewInstanceList.size()
    }
    
    void testListMaxParamBig() {
    	mockParams.max = 101
    	def model = controller.list()
    	assertEquals 'List size is incorrect', reviews.size(), model.reviewInstanceList.size()
    }
}
