package org.ammrf.tf

class Util {

	public static void checkFieldLength(theTest, target, fieldName, length, suffix) {
    	def maxSize = length + 1 - suffix.length()
    	StringBuilder bigText = new StringBuilder()
    	for(i in 1..maxSize) {
    		bigText.append('a')
    	}
    	bigText.append(suffix)
    	target."${fieldName}" = bigText
    	theTest.assertFalse 'Validation should have failed', target.validate()
    	theTest.assertEquals 'maxSize', target.errors."${fieldName}"
    }
	
	public static void checkFieldLength(theTest, target, fieldName, length) {
    	def maxSize = length + 1
    	StringBuilder bigText = new StringBuilder()
    	for(i in 1..maxSize) {
    		bigText.append('a')
    	}
    	target."${fieldName}" = bigText
    	theTest.assertFalse 'Validation should have failed', target.validate()
    	theTest.assertEquals 'maxSize', target.errors."${fieldName}"
    }

    public static void checkFieldBlank(theTest, target, fieldName) {
    	target."${fieldName}" = ""
    	theTest.assertFalse 'Validation should have failed', target.validate()
    	theTest.assertEquals 'blank', target.errors."${fieldName}"	
    }

    public static void checkUrlLength(theTest, target, fieldName, length) {
    	def domainPart = "http://localhost.com/"
    	def maxSize = length - domainPart.length() + 1
    	StringBuilder bigText = new StringBuilder()
    	bigText.append(domainPart)
    	for(i in 1..maxSize) {
    		bigText.append('a')
    	}
    	target."${fieldName}" = bigText
    	theTest.assertFalse 'Validation should have failed', target.validate()
    	theTest.assertEquals 'maxSize', target.errors."${fieldName}"
    }
    
    public static void checkFieldIsEmail(theTest, target, fieldName) {
    	target."${fieldName}" = "test"
    	theTest.assertFalse 'Validation should have failed', target.validate()
    	theTest.assertEquals 'email', target.errors."${fieldName}"	
    }
    
}
