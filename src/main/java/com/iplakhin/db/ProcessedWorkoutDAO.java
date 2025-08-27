package com.iplakhin.db;

import com.iplakhin.EMFactory;
import com.iplakhin.entities.ProcessedWorkout;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ProcessedWorkoutDAO {
    public static List<ProcessedWorkout> getWorkouts(Instant startDate, Instant endDate) {
        EntityManager em = EMFactory.getEntityManager();
        List<ProcessedWorkout> processedWorkoutList = new ArrayList<>();
        try {
            processedWorkoutList = em
                    .createQuery("FROM ProcessedWorkout WHERE workoutId BETWEEN :startDate AND :endDate", ProcessedWorkout.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();

        return processedWorkoutList;
    }

    public static void writeWorkouts(Queue<ProcessedWorkout> processedWorkoutQueue) {
        EntityManager em = EMFactory.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        for (ProcessedWorkout processedWorkout: processedWorkoutQueue) {
            if (processedWorkout.isEmpty()) {
                continue;
            } else {
                try {
                    transaction.begin();
                    em.persist(processedWorkout);
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
            }

        }

        em.close();
    }
}
