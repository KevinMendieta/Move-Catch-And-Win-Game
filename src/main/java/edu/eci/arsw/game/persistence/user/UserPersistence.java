/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.persistence.user;

import edu.eci.arsw.game.model.User;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Zekkenn
 */
@Component
public interface UserPersistence {
    
    
    /**
     * Register a new room in memory
     * @param user the new User.
     * @throws UserPersistenceException if the User already exists.
     */
    public void registerNewUser(User user) throws UserPersistenceException;
    
    /**
     * update an old user in memory
     * @param user the new User.
     * @throws UserPersistenceException if the User doesn't exists.
     */
    public void updateUser(User user) throws UserPersistenceException;
    
    /**
     * Return a user, is consulted by the unique id
     * @param id the user's id
     * @return The user corresponding to these id. 
     * @throws UserPersistenceException If there's not user associated with the id.
     */
    public User getUser(int id) throws UserPersistenceException;
    
    /**
     * Return a user, is consulted by the unique nickname
     * @param nickName the user's nickname
     * @return The user corresponding to these id. 
     * @throws UserPersistenceException If there's not user associated with the nickname.
     */
    public User getUserByNickname(String nickName) throws UserPersistenceException;
    
    /**
     * Return a user, is consulted by the unique email
     * @param email the user's email
     * @return The user corresponding to these id. 
     * @throws UserPersistenceException If there's not user associated with the email.
     */
    public User getUserByEmail(String email) throws UserPersistenceException;
    
    /**
     * Return a user, is consulted by the unique confirmation token
     * @param confirmation the user's confirmation token
     * @return The user corresponding to these id. 
     * @throws UserPersistenceException If there's not user associated with the confirmation token.
     */
    public User getUserByConfirmation(String confirmation) throws UserPersistenceException;
    
    /**
     * Return the next id of the last user
     * @return The next id. 
     */
    public int getNextId();
    
    /**
     * Return All users
     * @return All users. 
     */
    public Map<Integer, User> getAllUsers();

    
}
