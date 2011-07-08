package org.ammrf.tf.admin

import org.codehaus.groovy.grails.plugins.springsecurity.Secured;


@Secured(['ROLE_ADMIN'])
class AdminController {
    static allowedMethods = [index: "GET", debug: "GET"]
    
    def index = {
    }
    
    @Secured(['ROLE_SUPER'])
    def debug = {
    }
}
