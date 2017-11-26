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
    private final Map<String, User> emails = new ConcurrentHashMap<>();
    private final Map<String, User> confirmTokens = new ConcurrentHashMap<>();

    @Override
    public void registerNewUser(User user) throws UserPersistenceException {
        if ( users.containsKey(user.getId()) || nicks.containsKey(user.getNickname()) || emails.containsKey(user.getEmail())) 
            throw new UserPersistenceException("User already registered.");
        else {
            users.put(user.getId(), user); nicks.put(user.getNickname(), user); emails.put(user.getEmail(), user); confirmTokens.put(user.getConfirmation(), user);
        }
        for (int i = 1; i < 5; i++) {System.out.println("");}
        System.out.println(confirmTokens.get(user.getConfirmation()).getConfirmation());
        for (int i = 1; i < 5; i++) {System.out.println("");}
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

    @Override
    public User getUserByEmail(String email) throws UserPersistenceException {
        System.out.println("------------------------------ Wellcome: "+email+" ------------------------------");
        if ( email == null ) throw new UserPersistenceException("User not found.");
        if ( !emails.containsKey(email) )
            throw new UserPersistenceException("User not found.");
        else
            return emails.get(email);
    }

    @Override
    public User getUserByConfirmation(String confirmation) throws UserPersistenceException {
        for (int i = 1; i < 5; i++) {System.out.println("");}
        System.out.println("------------------------------ Wellcome: "+confirmation+" ------------------------------");
        for (int i = 1; i < 5; i++) {System.out.println("");}
        if ( confirmation == null ) throw new UserPersistenceException("User not found.");
        if ( !confirmTokens.containsKey(confirmation) )
            throw new UserPersistenceException("User not found.");
        else
            return confirmTokens.get(confirmation);
    }
    
}
