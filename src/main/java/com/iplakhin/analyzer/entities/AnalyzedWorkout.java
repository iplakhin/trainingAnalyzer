package com.iplakhin.analyzer.entities;

import jakarta.persistence.*;

import java.time.Instant;


public class AnalyzedWorkout {

    private Long id;

    //private Instant workoutId;

    private Integer duration;

    private Integer totalTimeWithValues;

    private Integer zone1;
    private Byte zone1Percent;

    private Integer zone2;
    private Byte zone2Percent;

    private Integer zone3;
    private Byte zone3Percent;

    private Integer zone4;
    private Byte zone4Percent;

    private Integer zone5;
    private Byte zone5Percent;


    public AnalyzedWorkout() {
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

    public AnalyzedWorkout(Integer duration, Integer totalTimeWithValues) {
        this.duration = duration;
        this.totalTimeWithValues = totalTimeWithValues;
        zone1 = 0;
        zone2 = 0;
        zone3 = 0;
        zone4 = 0;
        zone5 = 0;
        zone1Percent = 0;
        zone2Percent = 0;
        zone3Percent = 0;
        zone4Percent = 0;
        zone5Percent = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return false;
    }

    @Override
    public String toString() {
        return "ProcessedWorkout{" +
                "id=" + id +
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

