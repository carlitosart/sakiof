package com.sakila.sakila;

import com.joutvhu.dynamic.jpa.support.DynamicJpaRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@SpringBootApplication

@EnableJpaRepositories(repositoryFactoryBeanClass = DynamicJpaRepositoryFactoryBean.class)
public class SakilaApplication {
    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SakilaApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);

    }


}
@Component
class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String...args) throws Exception {
        System.out.println(passwordEncoder.encode("password"));

    }
}
