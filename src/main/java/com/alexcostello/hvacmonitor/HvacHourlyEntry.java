package com.alexcostello.hvacmonitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Defines POJO for entries in history_data_hourly.csv. Fields in the csv not needed to create daily usage
 * reports are ignored.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HvacHourlyEntry {

    @JsonFormat(pattern="MM/dd/yyyy H:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("Date time")
    public LocalDateTime dateTime;

    @JsonProperty("Temperature")
    public Double temp;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public Double getTemp() {
        return temp;
    }

}
