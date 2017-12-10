package edu.eci.arsw.gameapi;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author KevinMendieta
 */
@SpringBootApplication
@EnableMongoRepositories( basePackages = {"edu.eci.arsw.game.persistence.user"} )
@ComponentScan(basePackages = {"edu.eci.arsw.game", "edu.eci.arsw.game.persistence.user"})
public class MoveCatchAndWinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveCatchAndWinApiApplication.class, args);
    }

    @Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();
        realm.setUserDefinitions("prueba=prueba,user\n" + "admin=admin,admin");
        realm.setRoleDefinitions("admin=read,write\n" +
                                 "user=read");
        realm.setCachingEnabled(true);
        return realm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // use permissive to NOT require authentication, our controller Annotations will decide that
        chainDefinition.addPathDefinition("/**", "authcBasic");
        return chainDefinition;
    }
}
