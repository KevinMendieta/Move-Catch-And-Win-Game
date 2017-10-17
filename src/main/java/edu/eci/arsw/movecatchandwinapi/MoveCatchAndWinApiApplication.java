/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.movecatchandwinapi;

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
