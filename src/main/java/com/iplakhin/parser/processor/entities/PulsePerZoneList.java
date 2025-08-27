package com.iplakhin.parser.processor.entities;

import com.iplakhin.db.ZoneDAO;
import com.iplakhin.entities.HRZone;

import java.util.List;

public class PulsePerZoneList {
    private int zone1;
    private int zone2;
    private int zone3;
    private int zone4;
    private int zone5;

    public PulsePerZoneList() {
        this.zone1 = 0;
        this.zone2 = 0;
        this.zone3 = 0;
        this.zone4 = 0;
        this.zone5 = 0;
    }

    public int getZone1() {
        return zone1;
    }

    public void setZone1(int zone1) {
        this.zone1 = zone1;
    }

    public int getZone2() {
        return zone2;
    }

    public void setZone2(int zone2) {
        this.zone2 = zone2;
    }

    public int getZone3() {
        return zone3;
    }

    public void setZone3(int zone3) {
        this.zone3 = zone3;
    }

    public int getZone4() {
        return zone4;
    }

    public void setZone4(int zone4) {
        this.zone4 = zone4;
    }

    public int getZone5() {
        return zone5;
    }

    public void setZone5(int zone5) {
        this.zone5 = zone5;
    }

    public static PulsePerZoneList getMaxPulseFromZones() {
        List<HRZone> hrZones = ZoneDAO.getZones();
        PulsePerZoneList pulsePerZoneList = new PulsePerZoneList();

        if (!hrZones.isEmpty()) {
            pulsePerZoneList.setZone1(hrZones.get(0).getMaxPulse());
            pulsePerZoneList.setZone2(hrZones.get(1).getMaxPulse());
            pulsePerZoneList.setZone3(hrZones.get(2).getMaxPulse());
            pulsePerZoneList.setZone4(hrZones.get(3).getMaxPulse());
            pulsePerZoneList.setZone5(hrZones.get(4).getMaxPulse());
        } else {
            System.out.println("HRZones list is empty");
        }

        return pulsePerZoneList;
    }
}
