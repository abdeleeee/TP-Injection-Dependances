package org.example.presentation;

import org.example.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Presentation {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        IMetier metier = context.getBean("metier", IMetier.class);

        System.out.println("Résultat = " + metier.calcul());
    }
}