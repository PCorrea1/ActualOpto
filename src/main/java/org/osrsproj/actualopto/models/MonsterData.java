package org.osrsproj.actualopto.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MONSTER_STATS") // Adjust the table name if needed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonsterData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int elementalWeakness;

    private int elementalPercent;
    private int magicDefence;
    private int crushDefence;
    private int stabDefence;
    private int slashDefence;
    private int standardDefence;
    private int heavyDefence;
    private int lightDefence;

    public MonsterData(String name, int elementalWeakness, int elementalPercent, int magicDefence, int crushDefence, int stabDefence, int slashDefence, int standardDefence, int heavyDefence, int lightDefence) {
    }
}

