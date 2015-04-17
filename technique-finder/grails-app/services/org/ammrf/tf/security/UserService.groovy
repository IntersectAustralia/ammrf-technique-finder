package org.ammrf.tf.security

import org.hibernate.SessionFactory;

/**
 * Service to work with user accounts
 * 
 * @author Andrey Chernyshov
 *
 */
class UserService {

	static Long ROOT_SUPER_ACCOUNT_ID = 1
	
    boolean transactional = true
    
    SessionFactory sessionFactory

    /**
     * Persists new user into DB including associated roles
     * 
     * @param userInstance user to save
     * @return boolean to indicate if save was successful
     */
    def createUser(User userInstance) {
    	def savedSuccessfully = !userInstance.hasErrors() && userInstance.save() && Role.findByAuthority("ROLE_ADMIN").addToPeople(userInstance)
        if(userInstance.isSuper) {
        	savedSuccessfully = savedSuccessfully && Role.findByAuthority("ROLE_SUPER").addToPeople(userInstance)
        }
    	
    	def hibernateSession = sessionFactory.getCurrentSession()
    	hibernateSession.flush()
    	
    	return savedSuccessfully
    }
    
    /**
     * Deletes user from DB including associated roles
     * @param userInstance user to delete
     * 
     */
    def deleteUser(User userInstance) {
    	Role.list().each{it.removeFromPeople(userInstance)}
        userInstance.delete(flush:true)
    }
    
    /**
     * Updates user with new data including associated roles.
     * This method should be called only by super admin
     * 
     * @param userInstance user to update
     * @return boolean to indicate if save was successful
     */
    def updateUserAndRoles(User userInstance) {
    	def savedSuccessfully = !userInstance.hasErrors() && userInstance.save()
        def superRole = Role.findByAuthority("ROLE_SUPER")
    	if(userInstance.isSuper) {
    		savedSuccessfully = savedSuccessfully && superRole.addToPeople(userInstance)
    	} else if(!ROOT_SUPER_ACCOUNT_ID.equals(userInstance.id)){
    		savedSuccessfully = savedSuccessfully && superRole.removeFromPeople(userInstance)
    	}
    	
    	def hibernateSession = sessionFactory.getCurrentSession()
    	hibernateSession.flush()
    	
    	return savedSuccessfully
    }
}
