package com.iplakhin.analyzer;

import com.iplakhin.analyzer.entities.AnalyzedWorkout;
import com.iplakhin.db.ProcessedWorkoutDAO;
import com.iplakhin.entities.ProcessedWorkout;

import java.time.Instant;
import java.util.List;

public class Analyzer {
    public static AnalyzedWorkout analyzeWorkouts(Instant startDate, Instant endDate) {
        AnalyzedWorkout analyzedWorkout = new AnalyzedWorkout();
        List<ProcessedWorkout> workoutsList = getWorkouts(startDate, endDate);

        if (workoutsList.isEmpty()) {
            return analyzedWorkout;
        }

        for (ProcessedWorkout processedWorkout: workoutsList) {
            analyzedWorkout.setDuration(analyzedWorkout.getDuration() + processedWorkout.getDuration());
            analyzedWorkout.setTotalTimeWithValues(analyzedWorkout.getTotalTimeWithValues() + processedWorkout.getTotalTimeWithValues());
            analyzedWorkout.setZone1(analyzedWorkout.getZone1() + processedWorkout.getZone1());
            analyzedWorkout.setZone2(analyzedWorkout.getZone2() + processedWorkout.getZone2());
            analyzedWorkout.setZone3(analyzedWorkout.getZone3() + processedWorkout.getZone3());
            analyzedWorkout.setZone4(analyzedWorkout.getZone4() + processedWorkout.getZone4());
            analyzedWorkout.setZone5(analyzedWorkout.getZone5() + processedWorkout.getZone5());
        }

        analyzedWorkout.setZone1Percent(countPercents(analyzedWorkout.getTotalTimeWithValues(), analyzedWorkout.getZone1()));
        analyzedWorkout.setZone2Percent(countPercents(analyzedWorkout.getTotalTimeWithValues(), analyzedWorkout.getZone2()));
        analyzedWorkout.setZone3Percent(countPercents(analyzedWorkout.getTotalTimeWithValues(), analyzedWorkout.getZone3()));
        analyzedWorkout.setZone4Percent(countPercents(analyzedWorkout.getTotalTimeWithValues(), analyzedWorkout.getZone4()));
        analyzedWorkout.setZone5Percent(countPercents(analyzedWorkout.getTotalTimeWithValues(), analyzedWorkout.getZone5()));


        return analyzedWorkout;
    }

    private static List<ProcessedWorkout> getWorkouts(Instant startDate, Instant endDate) {
        return ProcessedWorkoutDAO.getWorkouts(startDate, endDate);
    }

    private static Byte countPercents(int totalTimeSeconds, int secondsAmount) {
        if (secondsAmount == 0 || totalTimeSeconds == 0) {
            return 0;
        }
        return (byte) Math.round((double)secondsAmount / totalTimeSeconds * 100);
    }

}
