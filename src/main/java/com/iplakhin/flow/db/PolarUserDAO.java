package com.iplakhin.flow.db;

import com.iplakhin.EMFactory;
import com.iplakhin.flow.models.PolarUser;
import jakarta.persistence.EntityManager;


public class PolarUserDAO {
    public static PolarUser getPolarUser() {
        EntityManager em = EMFactory.getEntityManager();
        PolarUser user = new PolarUser();
        try {
            user = em.find(PolarUser.class, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();

        return user;
    }
}
