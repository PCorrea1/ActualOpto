package org.osrsproj.actualopto.scheduler;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeUnit;

@Component
public class CsvToOracleMonsters extends AbstractScheduledService {
    // Oracle connection details - REPLACE THESE
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // e.g., "jdbc:oracle:thin:@localhost:1521:XE"
    private static final String USERNAME = "SYSTEM";
    private static final String PASSWORD = "pabs3019";
    private static final String CSV_FILE = "monster-info/monsterdata.csv"; // Your CSV path
    private static final String TABLE_NAME = "MONSTER_STATS";


    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 90, TimeUnit.DAYS);
    }

    @Override
    protected void startUp() {
        System.out.println("Service starting...");
    }

    @Override
    public  void runOneIteration() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Step 1: Read CSV headers
            CSVReader csvReader = new CSVReader(new FileReader(
                    getClass().getClassLoader().getResource(CSV_FILE).getFile()
            ));
            String[] headers = csvReader.readNext();
            if (headers == null) throw new RuntimeException("CSV is empty");
            for (int i = 0; i < headers.length; i++) {
                headers[i] = headers[i].toUpperCase();
            }

            // Step 3: Prepare insert statement
            String insertSql = "INSERT INTO " + TABLE_NAME + " (" + String.join(",", headers) + ") VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            conn.setAutoCommit(false);

            // Step 4: Stream CSV rows
            String[] row;
            int batchSize = 0;
            while ((row = csvReader.readNext()) != null) {
                pstmt.setString(1, row[0]); // NAME
                pstmt.setString(2, row[1].isEmpty() ? null : row[1]); // ELEMENTAL_WEAKNESS
                pstmt.setObject(3, row[2].isEmpty() ? null : Integer.parseInt(row[2])); // ELEMENTAL_PERCENT
                pstmt.setObject(4, row[3].isEmpty() ? null :  Integer.parseInt(row[3])); // MAGIC_DEFENCE
                pstmt.setObject(5, row[4].isEmpty() ? null : Integer.parseInt(row[4])); // CRUSH_DEFENCE
                pstmt.setObject(6, row[5].isEmpty() ? null :  Integer.parseInt(row[5])); // STAB_DEFENCE
                pstmt.setObject(7, row[6].isEmpty() ? null :  Integer.parseInt(row[6])); // SLASH_DEFENCE
                pstmt.setObject(8, row[7].isEmpty() ? null :  Integer.parseInt(row[7])); // STANDARD_DEFENCE
                pstmt.setObject(9, row[8].isEmpty() ? null : Integer.parseInt(row[8])); // HEAVY_DEFENCE
                pstmt.setString(10, row[9]); // LIGHT_DEFENCE

                pstmt.addBatch();
                batchSize++;
                if (batchSize % 1000 == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    System.out.println("Processed " + batchSize + " rows");
                }
            }
            pstmt.executeBatch();
            conn.commit();
            System.out.println("Total rows processed: " + batchSize);

        } catch (SQLException e) {
            System.err.println("Oracle error: " + e.getMessage());
        } catch (IOException | CsvException | NumberFormatException e) {
            System.err.println("CSV error: " + e.getMessage());
        }
    }
}