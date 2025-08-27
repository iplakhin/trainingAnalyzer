package com.iplakhin.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(schema = "workouts", name = "workout")
public class ProcessedWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "workout_id", nullable = false)
    private Instant workoutId;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "time_with_values", nullable = false)
    private Integer totalTimeWithValues;

    @Column(name = "zone1", nullable = false)
    private Integer zone1;

    @Column(name = "zone1_percent", nullable = false)
    private Byte zone1Percent;

    @Column(name = "zone2", nullable = false)
    private Integer zone2;

    @Column(name = "zone2_percent", nullable = false)
    private Byte zone2Percent;

    @Column(name = "zone3", nullable = false)
    private Integer zone3;

    @Column(name = "zone3_percent", nullable = false)
    private Byte zone3Percent;

    @Column(name = "zone4", nullable = false)
    private Integer zone4;

    @Column(name = "zone4_percent", nullable = false)
    private Byte zone4Percent;

    @Column(name = "zone5", nullable = false)
    private Integer zone5;

    @Column(name = "zone5_percent", nullable = false)
    private Byte zone5Percent;


    public ProcessedWorkout() {
        this.workoutId = null;
        this.duration = 0;
        this.totalTimeWithValues = 0;
        this.zone1 = 0;
        this.zone1Percent = 0;
        this.zone2 = 0;
        this.zone2Percent = 0;
        this.zone3 = 0;
        this.zone3Percent = 0;
        this.zone4 = 0;
        this.zone4Percent = 0;
        this.zone5 = 0;
        this.zone5Percent = 0;
    }

    // добавить в конструктор все поля - он будет использоваться при получении данных из базы
    public ProcessedWorkout(Instant workoutId, Integer duration, Integer totalTimeWithValues) {
        this.workoutId = workoutId;
        this.duration = duration;
        this.totalTimeWithValues = totalTimeWithValues;
        zone1 = 0;
        zone2 = 0;
        zone3 = 0;
        zone4 = 0;
        zone5 = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Instant workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalTimeWithValues() {
        return totalTimeWithValues;
    }

    public void setTotalTimeWithValues(Integer totalTimeWithValues) {
        this.totalTimeWithValues = totalTimeWithValues;
    }

    public Integer getZone1() {
        return zone1;
    }

    public void setZone1(Integer zone1) {
        this.zone1 = zone1;
    }

    public Byte getZone1Percent() {
        return zone1Percent;
    }

    public void setZone1Percent(Byte zone1Percent) {
        this.zone1Percent = zone1Percent;
    }

    public Integer getZone2() {
        return zone2;
    }

    public void setZone2(Integer zone2) {
        this.zone2 = zone2;
    }

    public Byte getZone2Percent() {
        return zone2Percent;
    }

    public void setZone2Percent(Byte zone2Percent) {
        this.zone2Percent = zone2Percent;
    }

    public Integer getZone3() {
        return zone3;
    }

    public void setZone3(Integer zone3) {
        this.zone3 = zone3;
    }

    public Byte getZone3Percent() {
        return zone3Percent;
    }

    public void setZone3Percent(Byte zone3Percent) {
        this.zone3Percent = zone3Percent;
    }

    public Integer getZone4() {
        return zone4;
    }

    public void setZone4(Integer zone4) {
        this.zone4 = zone4;
    }

    public Byte getZone4Percent() {
        return zone4Percent;
    }

    public void setZone4Percent(Byte zone4Percent) {
        this.zone4Percent = zone4Percent;
    }

    public Integer getZone5() {
        return zone5;
    }

    public void setZone5(Integer zone5) {
        this.zone5 = zone5;
    }

    public Byte getZone5Percent() {
        return zone5Percent;
    }

    public void setZone5Percent(Byte zone5Percent) {
        this.zone5Percent = zone5Percent;
    }

    public boolean isEmpty() {
        return workoutId == null;
    }

    @Override
    public String toString() {
        return "ProcessedWorkout{" +
                "id=" + id +
                ", workoutId=" + workoutId +
                ", duration=" + duration +
                ", totalTimeWithValues=" + totalTimeWithValues +
                ", zone1=" + zone1 +
                ", zone1Percent=" + zone1Percent +
                ", zone2=" + zone2 +
                ", zone2Percent=" + zone2Percent +
                ", zone3=" + zone3 +
                ", zone3Percent=" + zone3Percent +
                ", zone4=" + zone4 +
                ", zone4Percent=" + zone4Percent +
                ", zone5=" + zone5 +
                ", zone5Percent=" + zone5Percent +
                '}';
    }
}
