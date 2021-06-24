package com.alexcostello.hvacmonitor.reports;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

/**
 * Defines java object of a daily HVAC usage report. For the date the report
 * represents object is used to track if heating or cooling were turned on
 * that day
 */
public class HvacDailyReport {
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate date;
    private boolean heatActivated;
    private boolean coolingActivated;

    public HvacDailyReport(LocalDate date) {
        this.date = date;
        this.heatActivated = false;
        this.coolingActivated = false;
    }

    public LocalDate getDate() { return date; }

    public boolean isHeatActivated() {
        return heatActivated;
    }

    public boolean isCoolingActivated() {
        return coolingActivated;
    }

    public void setHeatActivated() {
        heatActivated = true;
    }

    public void setCoolingActivated() {
        coolingActivated = true;
    }
}
