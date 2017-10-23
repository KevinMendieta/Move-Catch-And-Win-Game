/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.services;

import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserPersistence;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zekkenn
 */
@Service
public class LoginServices {
    
    private UserPersistence userPersistence;

    @Autowired
    public void setUserPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }
    
    /**
     * Register a new room in memory
     * @param user the new User.
     * @throws UserPersistenceException if the User already exists.
     */
    public void registerNewUser(User user) throws UserPersistenceException{
        userPersistence.registerNewUser(user);
    }
    
    /**
     * Return a user, is consulted by the unique id
     * @param id the user's id
     * @return The user corresponding to these id. 
     * @throws UserPersistenceException If there's not user associated with the id.
     */
    public User getUser(int id) throws UserPersistenceException{
        return userPersistence.getUser(id);
    }
    
}
