package application.controller;

import application.domain.StatisticPlayer;
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


        /*Team qac = repositoryTeam.findById((long) 1).get();
        Team boca = repositoryTeam.findById((long) 1).get();

        StatisticPlayer st1 = new StatisticPlayer(qac.getPlayers().get(1),1,1,0);
        StatisticPlayer st2 = new StatisticPlayer(boca.getPlayers().get(1),1,0,0);

        List<StatisticPlayer> statisticPlayers = new ArrayList<StatisticPlayer>();
        statisticPlayers.add(st1);
        statisticPlayers.add(st2);

        Game game = new Game(qac,boca, new DateTime("1977-10-06"),LocalTime.now(),1,1,1, statisticPlayers);

        repository.save(game);*/

        return (Collection<Game>) repository.findAll().stream()
                .collect(Collectors.toList());
    }
}
