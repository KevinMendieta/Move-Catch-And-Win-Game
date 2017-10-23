package edu.eci.arsw.gameapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author KevinMendieta
 */
@SpringBootApplication
@EnableAsync
@ComponentScan({"edu.eci.arsw.game.services"})
@EntityScan("edu.eci.arsw.game.model")
@EnableJpaRepositories("edu.eci.arsw.game.persistence.user")
@ComponentScan(basePackages = {"edu.eci.arsw.game"})
public class MoveCatchAndWinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveCatchAndWinApiApplication.class, args);
    }

}
