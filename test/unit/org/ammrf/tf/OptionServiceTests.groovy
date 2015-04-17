package org.ammrf.tf

import grails.test.*

class OptionServiceTests extends GrailsUnitTestCase {
	def service
	def options 
	
    protected void setUp() {
        super.setUp()
        options = []
        mockDomain(Option)
        for(i in 3..1) {
        	options += new Option(priority:i, name:"Test option $i", science:Science.BIOLOGY, type:OptionType.RIGHT).save(flush:true)
        }
        new Option(priority:1, name:"Test physics option", science:Science.PHYSICS, type:OptionType.RIGHT).save(flush:true)
        
        service = new OptionService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetOptions() {
    	def foundOptions = service.getOptions(Science.BIOLOGY, OptionType.RIGHT)
    	assertEquals 'Wrong options found', options.sort{it.priority}, foundOptions
    }
}
