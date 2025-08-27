package com.iplakhin.db;


import com.iplakhin.EMFactory;
import com.iplakhin.entities.HRZone;
import jakarta.persistence.EntityManager;


import java.util.ArrayList;
import java.util.List;

public class ZoneDAO {
    public static List getZones() {
        List<HRZone> hrZones = new ArrayList();

        try {
            EntityManager em = EMFactory.getEntityManager();
            hrZones = em.createQuery("from HRZone").getResultList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            for (HRZone zone: hrZones) {
                System.out.println(zone.toString());
            }
        }

    return hrZones;
    }
}
