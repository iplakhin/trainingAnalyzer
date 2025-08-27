package com.iplakhin.parser.processor;

import com.iplakhin.entities.ProcessedWorkout;
import com.iplakhin.parser.processor.entities.Workout;
import com.iplakhin.parser.processor.entities.PulsePerZoneList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class WorkoutProcessor {
    public static Queue<ProcessedWorkout> processWorkouts(Queue<Workout> workoutsQueue) {
        Queue<ProcessedWorkout> processedWorkoutsQueue = new LinkedList<>();

        if (workoutsQueue.isEmpty()) {
            return processedWorkoutsQueue;
        }

        while (!workoutsQueue.isEmpty()) {
            Workout workout = workoutsQueue.poll();
            processedWorkoutsQueue.add(processWorkout(workout));
        }
        return processedWorkoutsQueue;
    }

    private static ProcessedWorkout processWorkout(Workout workout) {
        ProcessedWorkout processedWorkout = new ProcessedWorkout();
        if (workout.isEmpty()) {
            return processedWorkout;
        }
        processedWorkout.setDuration(workout.getTotalTimeSeconds());
        processedWorkout.setTotalTimeWithValues(workout.getTotalTimeWithValues());

        PulsePerZoneList pulseZones = WorkoutProcessor.getZones();  // добавить проверки что не пустой список; загружать зоны однократно
        HashMap<Integer, Integer> heartRateValues = workout.getHeartRateValues();

        for (int pulse: heartRateValues.keySet()) { // потом добавить проверку на pulse = 0 || pulse < 0
            int currentValue = 0;

            if (pulse <= pulseZones.getZone1()) { // раскидывает пульс по зонам
                currentValue = processedWorkout.getZone1() + heartRateValues.get(pulse);
                processedWorkout.setZone1(currentValue);
            } else if (pulse > pulseZones.getZone1() && pulse <= pulseZones.getZone2()) {
                currentValue = processedWorkout.getZone2() + heartRateValues.get(pulse);
                processedWorkout.setZone2(currentValue);
            } else if (pulse > pulseZones.getZone2() && pulse <= pulseZones.getZone3()) {
                currentValue = processedWorkout.getZone3() + heartRateValues.get(pulse);
                processedWorkout.setZone3(currentValue);
            } else if (pulse > pulseZones.getZone3() && pulse <= pulseZones.getZone4()) {
                currentValue = processedWorkout.getZone4() + heartRateValues.get(pulse);
                processedWorkout.setZone4(currentValue);
            } else {
                currentValue = processedWorkout.getZone5() + heartRateValues.get(pulse);
                processedWorkout.setZone5(currentValue);
            }
        }

        try { // пишет проценты по зонам
            processedWorkout.setZone1Percent(countPercents(processedWorkout.getTotalTimeWithValues(), processedWorkout.getZone1()));
            processedWorkout.setZone2Percent(countPercents(processedWorkout.getTotalTimeWithValues(), processedWorkout.getZone2()));
            processedWorkout.setZone3Percent(countPercents(processedWorkout.getTotalTimeWithValues(), processedWorkout.getZone3()));
            processedWorkout.setZone4Percent(countPercents(processedWorkout.getTotalTimeWithValues(), processedWorkout.getZone4()));
            processedWorkout.setZone5Percent(countPercents(processedWorkout.getTotalTimeWithValues(), processedWorkout.getZone5()));

        } catch (Exception e) {
            System.out.println("Error when setting zone percents");
            throw new RuntimeException(e);
        }

        processedWorkout.setWorkoutId(workout.getId());

        System.out.println(processedWorkout);
        return processedWorkout;
    }

    private static PulsePerZoneList getZones() {
        return PulsePerZoneList.getMaxPulseFromZones();
    }

    private static Byte countPercents(int totalTimeSeconds, int secondsAmount) {
        if (secondsAmount == 0 || totalTimeSeconds == 0) {
            return 0;
        }
        return (byte) Math.round((double)secondsAmount / totalTimeSeconds * 100);
    }

}
