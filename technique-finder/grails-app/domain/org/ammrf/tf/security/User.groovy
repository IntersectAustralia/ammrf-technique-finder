package org.ammrf.tf.security


/**
 * User domain class.
 */
class User {
	static transients = ['password', 'passwordConfirmation', 'secondEmailConfirmation', 'isSuper']
	static hasMany = [authorities: Role]
	static belongsTo = Role

	/** Username (email that is used as account Id) */
	String username
	
	/** User full name */
	String fullName
	
	/** MD5 Password */
	String passwd
	
	/** enabled */
	boolean enabled = true

	/** plain password to create a MD5 password */
	String password
	
	/** plain pasword confirmation*/
	String passwordConfirmation
	
	/** Additional Email Address */
	String secondEmail
	
	/** Additional Email Address Confirmation */
	String secondEmailConfirmation
	
	/** If this user has ROLE_SUPER role */
	boolean isSuper

	static constraints = {
		username(blank: false, unique: true, email:true, maxSize:255)
		fullName(blank:false, maxSize:255)
		passwd(blank: false, maxSize:255)
		password(blank:false, maxSize:255)
		passwordConfirmation(blank:false, maxSize:255, validator:{val, obj ->
			if(!val.equals(obj.password)) return "tf.user.password.notMatch"
		})
		secondEmail(email:true, maxSize:255, validator:{val, obj ->
			if(obj.username.equals(val)) {
				return "tf.user.secondEmail.equalsUsername"
			} else {
				return true
			}
		})
		secondEmailConfirmation(email:true, maxSize:255, validator:{val, obj ->
			if(!val.equals(obj.secondEmail)){
				return "tf.user.secondEmail.notMatch"
			} else {
				return true
			}
		})
	}
}
