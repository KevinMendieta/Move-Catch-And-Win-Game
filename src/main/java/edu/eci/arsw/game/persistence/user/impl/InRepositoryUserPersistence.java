/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.persistence.user.impl;

import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserPersistence;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import edu.eci.arsw.game.persistence.user.UsersRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esteban
 */
public class InRepositoryUserPersistence implements UserPersistence{

    @Autowired
    UsersRepository userRepository;
    
    @Override
    public void registerNewUser(User user) throws UserPersistenceException {
        userRepository.save(user);
    }
    
    @Override
    public void updateUser(User user) throws UserPersistenceException {
        userRepository.save(user);
    }

    @Override
    public User getUser(int id) throws UserPersistenceException {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByNickname(String nickName) throws UserPersistenceException {
        return userRepository.findByNickname(nickName);
    }
    
    @Override
    public User getUserByEmail(String email) throws UserPersistenceException {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByConfirmation(String confirmation) throws UserPersistenceException {
        return userRepository.findByConfirmation(confirmation);
    }

    @Override
    public int getNextId() {
        return (int) userRepository.count();
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        Map<Integer, User> tmp = new HashMap<>();
        for ( User user : userRepository.findAll() ){
            tmp.put(user.getId(), user);
        }
        return tmp;
    }
    
}
