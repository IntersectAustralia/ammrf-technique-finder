package org.ammrf.tf.admin

import org.ammrf.tf.Contact;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

/**
 * Admin controller to list/vew/edit/delete Contacts
 * 
 * @author Andrey Chernyshov
 *
 */
@Secured(['ROLE_ADMIN'])
class ContactController {

    def scaffold = Contact
    
    /**
     * Lists available contacts sorted by name ascending
     */
    def list = {
        [contactInstanceList: Contact.findAll("from Contact as c order by c.location.priority asc, c.name")]
    }
}
