package application.repository;

import application.domain.Team;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(
        value = "SELECT * FROM team where championship_id is null", 
        nativeQuery = true)
    Collection<Team> findAllTeamsNotAssigned();

    @Query(
        value = "SELECT * FROM team t where championship_id = :id", 
        nativeQuery = true)
    ArrayList<Team> findTeamsByChampionshipId(@Param("id") String id);
}
