package com.alexcostello.hvacmonitor;

import com.alexcostello.hvacmonitor.reports.HvacDailyReport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(HvacDataController.class)
public class HvacDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests that the correct number of daily reports are generated for
     * the provided range.
     */
    @Test
    public void testGetReportFromDateInRange() throws Exception {
        String testGetUrl = "/hvac_data/reports/start=06-01-2020/end=06-05-2020";
        MvcResult result = mockMvc.perform(get(testGetUrl)).andReturn();
        JsonNode response = getResponseJson(result);
        assertEquals(5, response.size());
    }

    /**
     * Tests that an empty list is returned for date range there isn't
     * data for.
     */
    @Test
    public void testGetReportFromDateOutOfRange() throws Exception {
        String testGetUrl = "/hvac_data/reports/start=05-01-2020/end=05-05-2020";
        MvcResult result = mockMvc.perform(get(testGetUrl)).andReturn();
        JsonNode response = getResponseJson(result);
        assertEquals(0, response.size());
    }

    private JsonNode getResponseJson(MvcResult result) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(result.getResponse().getContentAsString());
    }

}
