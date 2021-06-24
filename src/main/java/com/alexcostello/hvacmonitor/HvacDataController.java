package com.alexcostello.hvacmonitor;

import com.alexcostello.hvacmonitor.reports.HvacDailyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hvac_data")
public class HvacDataController {

    @Autowired
    HvacDaoService hvacDaoService;

    /**
     * GetMapping for index page to return index.html from resources/templates. Serves
     * as landing page.
     */
    @GetMapping("/")
    public String index() { return "index"; }

    /**
     * Defines GetMapping for producing reports in the date range defined by path. Based
     * on range provided by path will return JSON formatted data of daily reports.
     */
    @GetMapping(value = "/reports/start={start}/end={end}")
    public Object getReportFromDateRange(@PathVariable("start") String start,
                                                        @PathVariable("end") String end) {
        List<HvacDailyReport> result = hvacDaoService.getUsageReportInRange(start, end);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

}
