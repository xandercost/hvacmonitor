package com.alexcostello.hvacmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.alexcostello.hvacmonitor")
public class HvacMonitorWebApp {

    public static void main (String[] args) {
        SpringApplication.run(HvacMonitorWebApp.class, args);
    }

}
