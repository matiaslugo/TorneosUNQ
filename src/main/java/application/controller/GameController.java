package application.controller;

import application.domain.*;
import application.dto.GameDTO;
import application.dto.StatisticPlayerDTO;
import application.repository.ChampionshipRepository;
import application.repository.GameRepository;
import application.repository.PlayerRepository;
import application.repository.TeamRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private PlayerRepository repositoryPlayer;

    @Autowired
    private ChampionshipRepository repositoryChampionship;

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

        if(game.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            String strDate = formatter.print(game.getDate());
            gameDTO.setDate(strDate);
        }

        if(game.getStartTime() != null) {
            gameDTO.setStartTime(game.getStartTime().toString());
        }

        gameDTO.setGoalsTeamA(game.getGoalsTeamA());
        gameDTO.setGoalsTeamB(game.getGoalsTeamB());
        gameDTO.setMatchweek(game.getMatchweek());

        return gameDTO;
    }

    @PostMapping(path ="/matchUpdate/{id}")
    public void matchUpdate(@PathVariable String id, @RequestBody GameDTO match) {

        Game gameById = repository.findById(Long.parseLong(id)).get();

        List<StatisticPlayerDTO> statisticPlayers = match.getStatisticPlayer();


        for (int i = 0; i < statisticPlayers.size(); ++i) {

            StatisticPlayerDTO stpCurrent = statisticPlayers.get(i);
            if(stpCurrent.getGoals() != 0 || stpCurrent.getYellowCard() != 0 || stpCurrent.getRedCard() != 0){

                StatisticPlayer newStatisticPlayer = new StatisticPlayer();

                Player player = repositoryPlayer.findById((long) stpCurrent.getId()).get();

                newStatisticPlayer.setPlayer(player);
                newStatisticPlayer.setGoals(stpCurrent.getGoals());
                newStatisticPlayer.setYellowCard(stpCurrent.getYellowCard());
                newStatisticPlayer.setRedCard(stpCurrent.getRedCard());

                gameById.addStatisticPlayer(newStatisticPlayer);
            }
        }

        gameById.setGoalsTeamA(match.getGoalsTeamA());
        gameById.setGoalsTeamB(match.getGoalsTeamB());
        gameById.setPlayed(true);

        repository.save(gameById);

        Championship championship = repositoryChampionship.findLastChampionship();

        Fixture fixture = championship.getFixture();

        fixture.setCurrentMatchWeek(match.getMatchweek());

        Positions positions;

        if(championship.getPositions().isEmpty()){

            positions = new Positions();
            positions.setName("Liga");

        } else {
            positions = championship.getPositions().get(0);
        }

        List<StatisticTeam> statisticTeams = positions.getStatisticTeams();

        boolean localizedTeamA = true;
        boolean localizedTeamB = true;

        for (int j = 0; j < statisticTeams.size(); ++j) {

            StatisticTeam sttCurrent = statisticTeams.get(j);

            Team teamCurrent = sttCurrent.getTeam();

            if(teamCurrent.getId() == match.getTeamAId()){

                sttCurrent.updateStatisticA(match);

                localizedTeamA = false;

            } else {
                if(teamCurrent.getId() == match.getTeamBId()){
                    sttCurrent.updateStatisticB(match);
                    localizedTeamB = false;
                }
            }
        }

        if(localizedTeamA == true){
            Team teamA = repositoryTeam.findById(match.getTeamAId()).get();
            StatisticTeam newStt = new StatisticTeam(teamA,0,0,0,0,0,0,0,0);

            newStt.updateStatisticA(match);

            positions.addStatisticTeams(newStt);
        }

        if(localizedTeamB == true){

            Team teamB = repositoryTeam.findById(match.getTeamBId()).get();
            StatisticTeam newStt = new StatisticTeam(teamB,0,0,0,0,0,0,0,0);

            newStt.updateStatisticB(match);

            positions.addStatisticTeams(newStt);
        }

        if(championship.getPositions().isEmpty()) {

            List<Positions> newPositions = new ArrayList<>();

            newPositions.add(positions);

            championship.setPositions(newPositions);
        }



        repositoryChampionship.save(championship);
    }
}
