package com.iplakhin.parser.processor.entities;

import java.time.Instant;
import java.util.HashMap;

public class Workout {
    private Instant id;
    private Integer totalTimeSeconds;
    private Integer totalTimeWithValues;
    private HashMap<Integer, Integer> heartRateValues;

    public Workout() {}

    public Workout(Instant id,
                   Integer totalTimeSeconds,
                   Integer totalTimeWithValues,
                   HashMap<Integer, Integer> heartRateValues) {
        this.id = id;
        this.totalTimeSeconds = totalTimeSeconds;
        this.totalTimeWithValues = totalTimeWithValues;
        this.heartRateValues = heartRateValues;
    }

    public Instant getId() {
        return id;
    }

    public void setId(Instant id) {
        this.id = id;
    }

    public Integer getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    public void setTotalTimeSeconds(Integer totalTimeSeconds) {
        this.totalTimeSeconds = totalTimeSeconds;
    }

    public Integer getTotalTimeWithValues() {
        return totalTimeWithValues;
    }

    public void setTotalTimeWithValues(Integer totalTimeWithValues) {
        this.totalTimeWithValues = totalTimeWithValues;
    }

    public HashMap<Integer, Integer> getHeartRateValues() {
        return heartRateValues;
    }

    public void setHeartRateValues(HashMap<Integer, Integer> heartRateValues) {
        this.heartRateValues = heartRateValues;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id='" + id + '\'' +
                ", totalTimeSeconds=" + totalTimeSeconds +
                ", totalTimeWithValues=" + totalTimeWithValues +
                ", heartRateValues=" + heartRateValues +
                '}';
    }

    public boolean isEmpty() {
        return id == null || totalTimeSeconds == 0 || totalTimeWithValues == 0 || heartRateValues.isEmpty();
    }
}
