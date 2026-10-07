package org.example;

import org.example.dao.DaoImpl;
import org.example.dao.IDao;
import org.example.metier.IMetier;
import org.example.metier.MetierImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public IDao dao() {
        return new DaoImpl();
    }

    @Bean
    public IMetier metier(IDao dao) {
        return new MetierImpl(dao);
    }
}