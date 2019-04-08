package application.controller;

import application.domain.Championship;
import application.domain.*;
import application.repository.ChampionshipRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@RestController
public class ChampionshipController {

    @Autowired
    private ChampionshipRepository repository;

    @GetMapping("/torneos")
    public Collection<Championship> getAll() {

        Player p1 = new Player("Victor","Zanardi");

        List<Player> players = new ArrayList<Player>();
        players.add(p1);

        Team t1 = new Team("LOS PIBES", players);

        List<Team> teams = new ArrayList<Team>();

        teams.add(t1);

        Championship c1 = new Championship("LA GILADA", "TODOS PUTOS", teams);

        repository.save(c1);


        return (Collection<Championship>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @PostMapping("/crearTorneo")
    public Championship crearTorneo(@Valid @RequestBody Championship torneo) {
        return repository.save(torneo);
    }


}
