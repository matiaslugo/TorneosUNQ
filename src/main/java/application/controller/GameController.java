package application.controller;

import application.domain.Player;
import application.domain.Statistic;
import application.domain.Team;
import application.repository.GameRepository;
import application.repository.TeamRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import application.domain.Game;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class GameController {

    @Autowired
    private GameRepository repository;

    @Autowired
    private TeamRepository repositoryTeam;


    @GetMapping("/games")
    public Collection<Game> getAll() {


        Team qac = repositoryTeam.findById((long) 87).get();
        Team boca = repositoryTeam.findById((long) 93).get();

        Statistic st1 = new Statistic(qac.getPlayers().get(1),1,1,0);
        Statistic st2 = new Statistic(boca.getPlayers().get(1),1,0,0);

        List<Statistic> statistics = new ArrayList<Statistic>();
        statistics.add(st1);
        statistics.add(st2);

        Game game = new Game(qac,boca, new DateTime("1977-10-06"),LocalTime.now(),1,1,statistics);

        repository.save(game);

        return (Collection<Game>) repository.findAll().stream()
                .collect(Collectors.toList());
    }
}
