/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.persistence.user;

import edu.eci.arsw.game.model.Player;
import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Zekkenn
 */
public interface UserPersistence {
    
    
    /**
     * Register a new room in memory
     * @param user the new User.
     * @throws UserPersistenceException if the User already exists.
     */
    public void registerNewUser(User user) throws UserPersistenceException;
    
    /**
     * Return a user, is consulted by the unique id
     * @param id the user's id
     * @return The user corresponding to these id. 
     * @throws UserPersistenceException If there's not user associated with the id.
     */
    public User getUser(int id) throws UserPersistenceException;
    
    /**
     * Return the current id of the last user
     * @return The current id. 
     */
    public int getCurrentId();
    
    /**
     * Return All users
     * @return All users. 
     */
    public Map<Integer, User> getAllUsers();

    
}
