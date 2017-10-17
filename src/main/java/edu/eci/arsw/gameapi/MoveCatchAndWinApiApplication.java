package edu.eci.arsw.gameapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author KevinMendieta
 */
@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.movecatchandwin"})
public class MoveCatchAndWinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveCatchAndWinApiApplication.class, args);
    }

}
