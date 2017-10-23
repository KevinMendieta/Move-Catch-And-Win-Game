package edu.eci.arsw.game.persistence.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.eci.arsw.game.model.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
         User findByEmail(String email);
	 User findByConfirmation(String confirmation);
}
