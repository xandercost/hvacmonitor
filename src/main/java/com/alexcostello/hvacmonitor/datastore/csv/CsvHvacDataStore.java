package com.alexcostello.hvacmonitor.datastore.csv;

import com.alexcostello.hvacmonitor.HvacHourlyEntry;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a class for interfacing with csv data. Reads from file from
 * path and maps entries in csv to HvacHourlyEntry POJOs to be used
 * by HvacDaoService.
 */
@Component("csvHvacDataStore")
public class CsvHvacDataStore {

    /*
        TODO this should be injected to allow more flexibility moving forward
     */
    private static final String csvPath = "src\\main\\resources\\history_data_hourly.csv";

    public List<HvacHourlyEntry> open() {
        try {
            File csvFile = new File(csvPath);
            return readFile(csvFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Reads file provided and attempts to map contents of the file to POJO definied
     * in HvacHourlyEntry. Returns list of all entries as java objects.
     * @param csv file to open
     * @return List of HvacHourlyEntries
     * @throws IOException
     */
    private static List<HvacHourlyEntry> readFile(File csv) throws IOException {
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        MappingIterator<HvacHourlyEntry> entryIterator = new CsvMapper()
                .readerFor(HvacHourlyEntry.class).with(schema).readValues(csv);

        return entryIterator.readAll();
    }
}
