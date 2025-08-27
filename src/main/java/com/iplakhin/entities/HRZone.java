package com.iplakhin.entities;

import jakarta.persistence.*;

@Entity
@Table(schema = "workouts", name = "zones")
public class HRZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private byte Id;

    @Column(name = "zone_id", nullable = false)
    private byte zoneId;

    @Column(name = "pulse_max", nullable = false)
    private int maxPulse;

    public HRZone() {}

    public HRZone(byte Id, byte zoneId, int maxPulse) {
        this.Id = Id;
        this.zoneId = zoneId;
        this.maxPulse = maxPulse;
    }

    public byte getZoneId() {
        return zoneId;
    }

    public void setZoneId(byte zoneId) {
        this.zoneId = zoneId;
    }

    public int getMaxPulse() {
        return maxPulse;
    }

    public void setMaxPulse(int maxPulse) {
        this.maxPulse = maxPulse;
    }

    @Override
    public String toString() {
        return "HRZone{" +
                "zoneId=" + zoneId +
                ", maxPulse=" + maxPulse +
                '}';
    }
}
