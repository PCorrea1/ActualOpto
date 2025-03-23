package org.osrsproj.actualopto.everythingelse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterDataRepository extends JpaRepository<MonsterData, Long> {
}
