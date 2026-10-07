package org.example;

import org.example.dao.DaoImpl;
import org.example.dao.IDao;
import org.example.metier.IMetier;
import org.example.metier.MetierImpl;

public class Main {

    public static void main(String[] args) {

        IDao dao = new DaoImpl();

        IMetier metier = new MetierImpl(dao);

        System.out.println("Résultat = " + metier.calcul());
    }
}