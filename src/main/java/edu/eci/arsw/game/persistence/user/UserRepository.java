package edu.eci.arsw.game.persistence.user;

import edu.eci.arsw.game.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findById(int id);
    User findByNickname(String nickName);
    User findByEmail(String email);
    User findByConfirmation(String confirmation);
}
