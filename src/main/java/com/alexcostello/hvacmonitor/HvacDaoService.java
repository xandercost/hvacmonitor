package com.alexcostello.hvacmonitor;

import com.alexcostello.hvacmonitor.datastore.csv.CsvHvacDataStore;
import com.alexcostello.hvacmonitor.reports.HvacDailyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Defines service that operates as intermediary between controller and
 * datastore.
 */
@Component
public class HvacDaoService {
    private final List<HvacHourlyEntry> hvacData;

    public HvacDaoService(@Autowired CsvHvacDataStore csvHvacDataStore) {
        hvacData = csvHvacDataStore.open();
    }

    /**
     * Produces a list of HvacDailyReports based on HvacHourlyEntries in the
     * provided date range. Daily reports are only created for dates in the
     * range for which data exists.
     * @param start startDate for range, inclusive, of format MM-dd-yyyy
     * @param end endDate for range, inclusive, of format MM-dd-yyyy
     * @return List of daily reports in date range. Empty list returned if
     *          no data is found in provided range.
     */
    public List<HvacDailyReport> getUsageReportInRange(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);

        List<HvacHourlyEntry> entriesInRange = getEntriesInDateRange(startDate, endDate);

        HashMap<LocalDate, HvacDailyReport> dailyReports = new HashMap<>();

        double heatActivatedTemp = 62.0;
        double coolingActivatedTemp = 75.0;

        for (HvacHourlyEntry e : entriesInRange) {
            HvacDailyReport dailyReport = dailyReports.get(e.getDate());
            if (dailyReport==null) {
                dailyReports.put(e.getDate(), new HvacDailyReport(e.getDate()));
                dailyReport = dailyReports.get(e.getDate());
            }
            if (e.getTemp() > heatActivatedTemp && e.getTemp() < coolingActivatedTemp)
                continue;
            if (dailyReport.isCoolingActivated() && dailyReport.isHeatActivated())
                continue;
            if (!dailyReport.isCoolingActivated() && e.getTemp() >= coolingActivatedTemp)
                    dailyReport.setCoolingActivated();
            else if (!dailyReport.isHeatActivated() && e.getTemp() <= heatActivatedTemp)
                    dailyReport.setHeatActivated();
        }

        return new ArrayList<>(dailyReports.values());
    }

    /**
     * Finds HvacHourlyEntries in between the inclusive provided date range.
     * Entries are included if DateTime of entry is after or equal to the
     * start of day for start and before or equal to 23:59 of the end.
     *
     * @param start MM-dd-yyyy date for start of range to include
     * @param end MM-dd-yyyy date for end of range to include
     * @return list of HvacHourEntry objects that satisfy range constraints
     */
    protected List<HvacHourlyEntry> getEntriesInDateRange(LocalDate start, LocalDate end) {
        List<HvacHourlyEntry> result = new ArrayList<>();
        for (HvacHourlyEntry e : hvacData) {
            if (e.getDateTime().compareTo(start.atStartOfDay()) >= 0
                    && e.getDateTime().compareTo(end.atTime(23, 59)) <= 0)
                result.add(e);
        }
        return result;
    }

}
