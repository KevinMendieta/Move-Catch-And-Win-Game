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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    
    /*static{
        users = new ConcurrentHashMap<>();
        nicks = new ConcurrentHashMap<>();
        emails = new ConcurrentHashMap<>();
        confirmTokens = new ConcurrentHashMap<>();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User default1 = new User(); default1.setId(0); default1.setNickname("Timmy"); default1.setPassword(bCryptPasswordEncoder.encode("prueba123")); default1.setConfirmation("default1"); default1.setEmail("default1@gmail.com"); default1.setEnabled(true);
        User default2 = new User(); default1.setId(1); default1.setNickname("Francis"); default1.setPassword(bCryptPasswordEncoder.encode("prueba123")); default1.setConfirmation("default2"); default1.setEmail("default2@gmail.com"); default1.setEnabled(true);
        users.put(default1.getId(), default1); nicks.put(default1.getNickname(), default1); emails.put(default1.getEmail(), default1); confirmTokens.put(default1.getConfirmation(), default1);
        users.put(default2.getId(), default2); nicks.put(default2.getNickname(), default2); emails.put(default2.getEmail(), default2); confirmTokens.put(default2.getConfirmation(), default2);
    }*/

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
