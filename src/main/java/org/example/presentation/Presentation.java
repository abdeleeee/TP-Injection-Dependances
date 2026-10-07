package org.example.presentation;

import org.example.AppConfig;
import org.example.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Presentation {

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        IMetier metier = context.getBean("metier", IMetier.class);

        System.out.println("Résultat = " + metier.calcul());
    }
}