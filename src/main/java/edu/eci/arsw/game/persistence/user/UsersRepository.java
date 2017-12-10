/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.game.persistence.user;

import edu.eci.arsw.game.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zekkenn
 */
@Service
public interface UsersRepository extends MongoRepository<User, Integer>{
    
    public User findById(Integer id);
    public User findByNickname(String nickname);
    public User findByEmail(String email);
    public User findByConfirmation(String confirmation);
}
