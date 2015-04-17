package org.ammrf.tf.admin

import org.ammrf.tf.OptionCombination;
import org.codehaus.groovy.grails.plugins.springsecurity.Secured;

@Secured(['ROLE_ADMIN'])
class OptionCombinationController {

	def scaffold = OptionCombination
}
