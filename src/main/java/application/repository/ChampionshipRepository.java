package application.repository;

import application.domain.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChampionshipRepository extends JpaRepository<Championship, Long> {

    @Query(
        value = "Select * from championship where id = (select max(id) from championship)", 
        nativeQuery = true)
    Championship findLastChampionship();

}
