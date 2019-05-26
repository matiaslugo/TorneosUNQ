package application.controller;

import application.domain.Championship;
import application.domain.*;
import application.repository.ChampionshipRepository;
import application.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ChampionshipController {

    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private TeamRepository repositoryTeam;

    @GetMapping("/torneos")
    public Collection<Championship> getAll() {
        //QUITAR EL COMENTARIO PARA LA PRIMERA VEZ DE LA EJECUCION
        //Team qac = repositoryTeam.findById((long) 1).get();

        /*Team qac = repositoryTeam.findById((long) 1).get();

        Team boca = repositoryTeam.findById((long) 7).get();

        StatisticTeam st1 = new StatisticTeam(qac,1,3,1,0,0,3,2,1);
        StatisticTeam st2 = new StatisticTeam(boca,1,0,0,0,1,2,3,-1);

        List<StatisticTeam> statisticTeams = new ArrayList<StatisticTeam>();
        statisticTeams.add(st1);
        statisticTeams.add(st2);

        Positions posit = new Positions(statisticTeams,"Torneo");

        List<Positions> positions = new ArrayList<Positions>();

        positions.add(posit);

        Championship ch = new Championship("TORNEO UNQ","ALTO TORNEO",new DateTime("1977-10-06"),new DateTime("1977-12-06"),positions,new Fixture());

        repository.save(ch);*/

        return (Collection<Championship>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @PostMapping("/crearTorneo")
    public Championship crearTorneo(@Valid @RequestBody Championship torneo) {
        return repository.save(torneo);
    }


    @GetMapping("/tablePositions")
    public Collection<StatisticTeam> getStatisticTeam() {

        try {
            return (Collection<StatisticTeam>) repository.findAll().get(0).getPositions().get(0).getStatisticTeams().stream()
                    .collect(Collectors.toList());

        }
        catch(Exception e){
            return new ArrayList<StatisticTeam>();
        }

    }

    @GetMapping("/matches")
    public Collection<Game> getGames() {

        Fixture currentFixture = repository.findAll().get(0).getFixture();

        Collection<Game> matches = (Collection<Game>) currentFixture.getGame().stream()
                .collect(Collectors.toList());

        return matches;
    }

    @GetMapping("/fixture")
    public int getFixture() {

        Fixture currentFixture = repository.findAll().get(0).getFixture();

        return currentFixture.getCurrentMatchWeek();
    }

}
