package org.osrsproj.actualopto.everythingelse;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "monster_stats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonsterData {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private String elementalWeakness;

    private Integer elementalPercent;
    private Integer magicDefence;
    private Integer crushDefence;
    private Integer stabDefence;
    private Integer slashDefence;
    private Integer standardDefence;
    private Integer heavyDefence;
    private Integer lightDefence;
}

