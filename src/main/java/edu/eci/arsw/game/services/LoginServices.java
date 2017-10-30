/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.services;

import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserPersistence;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.shiro.mgt.SecurityManager;

/**
 *
 * @author Zekkenn
 */
@Service
@Component
public class LoginServices {
    
    private UserPersistence userPersistence;

    @Autowired
    private SecurityManager securityManager;

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
    
    /**
     * Return the current id of the last user
     * @return The current id. 
     */
    public int getNextId(){
        return userPersistence.getNextId();
    }
    
    /**
     * Return All users
     * @return All users. 
     */
    public Map<Integer, User> getAllUsers(){
        return userPersistence.getAllUsers();
    }
    
    /**
     * Return All users
     * @return All users. 
     * @throws UserPersistenceException 
     */
    public User getCurrentUser() throws UserPersistenceException{
        Subject subject = SecurityUtils.getSubject();
        return userPersistence.getUserByNickname( (String) subject.getPrincipal() );
    }
    
    /**
     * Login the user
     * @param user the user
     * @throws UserPersistenceException if there is an user logged
     */
    public void login(User user) throws UserPersistenceException{
        Subject subject = SecurityUtils.getSubject();
        if ( subject.isAuthenticated() ) throw new UserPersistenceException("There exists an authenticated user.");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getNickname(), user.getPassword());
        token.setRememberMe(true);
        try {
            subject.login(token);
        } catch ( IncorrectCredentialsException | UnknownAccountException  ex ){
            throw new UserPersistenceException("Password didn't match, try again.");
        }
    }
    
    /**
     * Sets the static instance of SecurityManager. This is NOT needed for web applications.
     */
    @PostConstruct
    private void initStaticSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager);
    }
    
}
