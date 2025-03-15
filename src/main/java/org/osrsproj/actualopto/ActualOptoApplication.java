package org.osrsproj.actualopto;

import org.osrsproj.actualopto.scheduler.CsvToOracleMonsters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActualOptoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActualOptoApplication.class, args);

        CsvToOracleMonsters service = new CsvToOracleMonsters();
        service.startAsync(); // Start the service
        try {
            Thread.sleep(Long.MAX_VALUE); // Keep main thread alive (for demo)
        } catch (InterruptedException e) {
            service.stopAsync();
        }

    }

}
