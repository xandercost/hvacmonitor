package com.alexcostello.hvacmonitor;

import com.alexcostello.hvacmonitor.datastore.csv.CsvHvacDataStore;
import com.alexcostello.hvacmonitor.reports.HvacDailyReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HvacDaoServiceTest {
    private static HvacDaoService hvacDaoService;

    @BeforeAll
    public static void init() {
        CsvHvacDataStore csvHvacDataStore = new CsvHvacDataStore();
        hvacDaoService = new HvacDaoService(csvHvacDataStore);
    }

    /**
     * Verify that given a range of a day we produce the expected 24 hourly reports
     */
    @Test
    public void testGetInRange() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate startDate = LocalDate.parse("06-01-2020", formatter);
        LocalDate endDate = LocalDate.parse("06-01-2020", formatter);
        int expectedEntryCount = 24;
        List<HvacHourlyEntry> entryList = hvacDaoService.getEntriesInDateRange(startDate, endDate);
        assertEquals(expectedEntryCount, entryList.size());
    }

    /**
     * Verify the correctness of report on day where we know both heat and cooling
     * were turned on.
     */
    @Test
    public void testDailyReportCorrectness() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String start = "06-02-2020";
        String end = "06-02-2020";
        LocalDate expectedDate = LocalDate.parse(start, formatter);
        HvacDailyReport expectedReport = new HvacDailyReport(expectedDate);
        expectedReport.setHeatActivated();
        expectedReport.setCoolingActivated();

        List<HvacDailyReport> results = hvacDaoService.getUsageReportInRange(start, end);
        HvacDailyReport actual = results.get(0);

        assertEquals(1, results.size());
        assertEquals(expectedReport.getDate(), actual.getDate());
        assertEquals(expectedReport.isCoolingActivated(), actual.isCoolingActivated());
        assertEquals(expectedReport.isHeatActivated(), actual.isHeatActivated());
    }
}
