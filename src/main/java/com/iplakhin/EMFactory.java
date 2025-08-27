package com.iplakhin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class EMFactory {
    private static EntityManagerFactory EMF;

        public static EntityManager getEntityManager() {
            if (EMF == null) {
                EMF = Persistence.createEntityManagerFactory("workouts");
            }
            return EMF.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (EMF != null) {
            EMF.close();
        }
    }

}
