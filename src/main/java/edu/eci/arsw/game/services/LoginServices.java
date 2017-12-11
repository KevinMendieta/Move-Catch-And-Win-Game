/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.services;

import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserPersistence;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Zekkenn
 */
@Service
@Component
public class LoginServices implements UserDetailsService{
    
    private UserPersistence userPersistence;
    private JavaMailSender sender;

    @Autowired
    private SecurityManager securityManager;

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }
    
    @Autowired
    public void setUserPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }
    
    @Override
    public UserDetails loadUserByUsername(String string) {
        User user = null;
        try {
            user = getUserByNickname(string);
        } catch (UserPersistenceException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (user == null || user.isEnabled() == false){
            throw new BadCredentialsException("User details not found with this username: " + string);
        }
        
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return new org.springframework.security.core.userdetails.User(user.getNickname(),user.getPassword(),authList);
    }
    
    /**
     * Register a new room in memory
     * @param user the new User.
     * @throws UserPersistenceException if the User already exists.
     */
    public void saveUser(User user) throws UserPersistenceException{
        user.setId(getNextId());
        userPersistence.registerNewUser(user);
    }
    
    /**
     * Update an existing user in memory
     * @param user the new User.
     * @throws UserPersistenceException if the User doesn't exists.
     */
    public void updateUser(User user) throws UserPersistenceException{
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
     * Return a user, is consulted by the unique nickname
     * @param nickname the user's nickname
     * @return The user corresponding to these nickname. 
     * @throws UserPersistenceException If there's not user associated with the nickname.
     */
    public User getUserByNickname(String nickname) throws UserPersistenceException{
        return userPersistence.getUserByNickname(nickname);
    }
    
    /**
     * Return a user, is consulted by the unique email
     * @param email the user's email
     * @return The user corresponding to these email. 
     * @throws UserPersistenceException If there's not user associated with the email.
     */
    public User getUserByEmail(String email) throws UserPersistenceException{
        return userPersistence.getUserByEmail(email);
    }
    
    /**
     * Return a user, is consulted by the unique confirmation token
     * @param confirmation the user's confirmation token
     * @return The user corresponding to these confirmation token. 
     * @throws UserPersistenceException If there's not user associated with the confirmation token.
     */
    public User getUserByConfirmation(String confirmation) throws UserPersistenceException{
        return userPersistence.getUserByConfirmation(confirmation);
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
    
    @Async
    public void sendConfirmationEmail(SimpleMailMessage email) {
        this.sender.send(email);
    }
    
    /**
     * Sets the static instance of SecurityManager. This is NOT needed for web applications.
     */
    @PostConstruct
    private void initStaticSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager);
    }

    
    
}
