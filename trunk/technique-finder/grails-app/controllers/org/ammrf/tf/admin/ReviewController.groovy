package org.ammrf.tf.admin

import org.ammrf.tf.Review;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

@Secured(['ROLE_ADMIN'])
class ReviewController {
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
	
    def scaffold = Review
    
    /**
     * Lists available review sorted by title ascending
     */
    def list = {
        [reviewInstanceList: Review.list(sort:'title')]
    }
}
