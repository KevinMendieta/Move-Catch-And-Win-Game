/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.persistence.user.impl;

import edu.eci.arsw.game.model.Room;
import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserPersistence;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Zekkenn
 */
public class InMemoryUserPersistence implements UserPersistence{
    
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public void registerNewUser(User user) throws UserPersistenceException {
        if ( users.containsKey(user.getId()) ) 
            throw new UserPersistenceException("User already registered.");
        else
            users.put(user.getId(), user);
    }

    @Override
    public User getUser(int id) throws UserPersistenceException {
        if ( !users.containsKey(id) )
            throw new UserPersistenceException("User not found.");
        else
            return users.get(id);
    }
    
}
