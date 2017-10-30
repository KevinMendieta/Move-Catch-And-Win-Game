/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.persistence.user.impl;

import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserPersistence;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zekkenn
 */
@Service
public class InMemoryUserPersistence implements UserPersistence{
    
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Map<String, User> nicks = new ConcurrentHashMap<>();

    @Override
    public void registerNewUser(User user) throws UserPersistenceException {
        if ( users.containsKey(user.getId()) || nicks.containsKey(user.getNickname()) ) 
            throw new UserPersistenceException("User already registered.");
        else {
            users.put(user.getId(), user); nicks.put(user.getNickname(), user);
        }
    }

    @Override
    public User getUser(int id) throws UserPersistenceException {
        if ( !users.containsKey(id) )
            throw new UserPersistenceException("User not found.");
        else
            return users.get(id);
    }

    @Override
    public int getNextId() {
        return users.size();
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserByNickname(String nickName) throws UserPersistenceException {
        System.out.println("------------------------------ Wellcome: "+nickName+" ------------------------------");
        if ( nickName == null ) throw new UserPersistenceException("User not found.");
        if ( !nicks.containsKey(nickName) )
            throw new UserPersistenceException("User not found.");
        else
            return nicks.get(nickName);
    }
    
}
