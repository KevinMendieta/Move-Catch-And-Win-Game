package edu.eci.arsw.gameapi;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author KevinMendieta
 */
@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.game"})
public class MoveCatchAndWinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveCatchAndWinApiApplication.class, args);
    }

    @Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();
        realm.setUserDefinitions("prueba=prueba, user\nadmin=admin, admin");
        return realm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // use permissive to NOT require authentication, our controller Annotations will decide that
        chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
        return chainDefinition;
    }
}
