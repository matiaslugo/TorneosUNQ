package application.controller;

import application.domain.StatisticPlayer;
import application.domain.Team;
import application.dto.GameDTO;
import application.repository.GameRepository;
import application.repository.TeamRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import application.domain.Game;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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


        /*Team qac = repositoryTeam.findById((long) 24).get();
        Team boca = repositoryTeam.findById((long) 30).get();

        StatisticPlayer st1 = new StatisticPlayer(qac.playersAll().get(1),1,1,0);
        StatisticPlayer st2 = new StatisticPlayer(boca.playersAll().get(1),1,0,0);

        List<StatisticPlayer> statisticPlayers = new ArrayList<StatisticPlayer>();
        statisticPlayers.add(st1);
        statisticPlayers.add(st2);

        Game game = new Game(qac,boca, new DateTime("1977-10-06"), LocalTime.now(),1,1,1, statisticPlayers);

        repository.save(game);*/

        return (Collection<Game>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/matchBy/{id}")
    public GameDTO TeamBy(@PathVariable String id) {

        Game game = repository.findById(Long.parseLong(id)).get();
        GameDTO gameDTO = new GameDTO();
        gameDTO.setTeamAId(game.getTeamA().getId());
        gameDTO.setTeamAName(game.getTeamA().getName());
        gameDTO.setTeamBId(game.getTeamB().getId());
        gameDTO.setTeamBName(game.getTeamB().getName());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
        String strDate = formatter.print(game.getDate());
        gameDTO.setDate(strDate);
        gameDTO.setStartTime(game.getStartTime().toString());
        gameDTO.setGoalsTeamA(game.getGoalsTeamA());
        gameDTO.setGoalsTeamB(game.getGoalsTeamB());
        gameDTO.setMatchweek(game.getMatchweek());

        return gameDTO;
    }

    @PostMapping(path ="/matchUpdate/{id}")
    public void matchUpdate(@PathVariable String id, @RequestBody GameDTO match) {

        Optional<Game> gameById = repository.findById(Long.parseLong(id));

        gameById.get().setGoalsTeamA(match.getGoalsTeamA());
        gameById.get().setGoalsTeamB(match.getGoalsTeamB());

        repository.save(gameById.get());
    }
}
