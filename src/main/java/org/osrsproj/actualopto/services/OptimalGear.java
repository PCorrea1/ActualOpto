package org.osrsproj.actualopto.services;

import org.osrsproj.actualopto.models.MonsterData;

import java.sql.*;

public class OptimalGear {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "SYSTEM";
    private static final String PASSWORD = "pabs3019";
    private static final String CSV_FILE = "monster-info/monsterdata.csv";
    private static final String TABLE_NAME = "MONSTER_STATS";

    public static String runOptimalGear(String username, String bossName, String budget) {

        MonsterData boss = getMonsterData(bossName);





        return null;
    }

    private static MonsterData getMonsterData(String monsterName) {

        String sql = "SELECT * FROM MONSTER_STATS WHERE Name = 'TzTok-Jad'";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToMonster(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static MonsterData mapResultSetToMonster(ResultSet rs) throws SQLException {
        return new MonsterData(
                rs.getString("name"),
                rs.getInt("elemental_weakness"),
                rs.getInt("elemental_percent"),
                rs.getInt("magic_defence"),
                rs.getInt("crush_defence"),
                rs.getInt("stab_defence"),
                rs.getInt("slash_defence"),
                rs.getInt("standard_defence"),
                rs.getInt("heavy_defence"),
                rs.getInt("light_defence")
        );
    }
}
