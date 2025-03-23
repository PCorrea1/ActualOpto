package org.osrsproj.actualopto.everythingelse;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class CsvToOracleMonsters extends AbstractScheduledService {

    @Autowired
    private MonsterDataRepository monsterDataRepository;

    private static final String CSV_FILE = "monster-info/monsterdata.csv";

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 90, TimeUnit.DAYS);
    }

    @Override
    protected void startUp() {
        System.out.println("Service starting...");
    }

    @Override
    public void runOneIteration() {
        try (CSVReader csvReader = new CSVReader(new FileReader(
                getClass().getClassLoader().getResource(CSV_FILE).getFile()))) {
            String[] headers = csvReader.readNext();
            if (headers == null) throw new RuntimeException("CSV is empty");

            for (int i = 0; i < headers.length; i++) {
                headers[i] = headers[i].toUpperCase();
            }

            List<MonsterData> monsters = new ArrayList<>();
            String[] row;
            while ((row = csvReader.readNext()) != null) {

                Long id = (long) (monsters.size() + 1);
                MonsterData monsterData = new MonsterData();
                monsterData.setId(id);
                monsterData.setName(row[0]);
                monsterData.setElementalWeakness(emptyToNull(row[1]));
                monsterData.setElementalPercent(parseNullableInt(row[2]));
                monsterData.setMagicDefence(parseNullableInt(row[3]));
                monsterData.setCrushDefence(parseNullableInt(row[4]));
                monsterData.setStabDefence(parseNullableInt(row[5]));
                monsterData.setSlashDefence(parseNullableInt(row[6]));
                monsterData.setStandardDefence(parseNullableInt(row[7]));
                monsterData.setHeavyDefence(parseNullableInt(row[8]));
                monsterData.setLightDefence(parseNullableInt(row[9]));

                monsters.add(monsterData);
            }
            monsterDataRepository.saveAll(monsters);
            System.out.println("Total rows processed: " + monsters.size());

        } catch (IOException | CsvException | NumberFormatException e) {
            System.err.println("CSV error: " + e.getMessage());
        }
    }

    private static Integer parseNullableInt(String val) {
        return (val == null || val.isEmpty()) ? null : Integer.parseInt(val);
    }

    private static String emptyToNull(String val) {
        return (val == null || val.isEmpty()) ? null : val;
    }

}