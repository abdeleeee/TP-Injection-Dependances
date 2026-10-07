package org.example.presentation;

import org.example.dao.IDao;
import org.example.metier.IMetier;

public class Presentation {

    public static void main(String[] args) throws Exception {

        // Instanciation dynamique de DaoImpl
        Class<?> cDao = Class.forName("org.example.dao.DaoImpl");
        IDao dao = (IDao) cDao.getDeclaredConstructor().newInstance();

        // Instanciation dynamique de MetierImpl
        Class<?> cMetier = Class.forName("org.example.metier.MetierImpl");
        IMetier metier = (IMetier) cMetier
                .getDeclaredConstructor(IDao.class)
                .newInstance(dao);

        System.out.println("Résultat = " + metier.calcul());
    }
}