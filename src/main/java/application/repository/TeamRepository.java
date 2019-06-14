package application.repository;

import application.domain.Team;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(
        value = "SELECT * FROM torneo_unq.team where championship_id is null", 
        nativeQuery = true)
    Collection<Team> findAllTeamsNotAssigned();
}
