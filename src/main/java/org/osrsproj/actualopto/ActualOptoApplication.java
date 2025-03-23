package org.osrsproj.actualopto;

import org.osrsproj.actualopto.everythingelse.CsvToOracleMonsters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ActualOptoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ActualOptoApplication.class, args);

        CsvToOracleMonsters service = context.getBean(CsvToOracleMonsters.class);
        service.startAsync();
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            service.stopAsync();
        }

    }

}
