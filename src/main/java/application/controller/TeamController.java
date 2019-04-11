package application.controller;

import application.domain.Championship;
import application.domain.Player;
import application.domain.Team;
import application.repository.ChampionshipRepository;
import application.repository.TeamRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RestController
public class TeamController {

    @Autowired
    private TeamRepository repository;

    @GetMapping("/teams")
    public Collection<Team> getAll() {


        return (Collection<Team>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/TeamBy/{id}")
    public  Collection<Player> TeamBy(@PathVariable String id) {

      return  repository.findById(Long.parseLong(id))
                .get()
                .getPlayers()
                .stream()
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/crearEquipo", method = {RequestMethod.GET,RequestMethod.POST})
//    @PostMapping(path="/crearEquipo")
    public void createTeam()
    {
        Player player1 = new Player("Sergio","Aguero",36158933,new DateTime("1980-12-04"),true);

        Player player2 = new Player("Lionel","Messi",32345677,new DateTime("1977-10-06"),true);

        Player player3 = new Player("Diego","Maradona",13245568,new DateTime("1970-07-30"),true);

        Player player4 = new Player("Victor","Zanardi",35234567,new DateTime("1990-04-09"),true);

        Player player5 = new Player("Fernando","Rodriguez",35456783,new DateTime("1988-09-05"),true);

        List<Player> players = new ArrayList<Player>();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Team equipoNew = new Team();
        equipoNew.setName("Los Galacticos");
        equipoNew.setPlayers(players);

        repository.save(equipoNew);
    }



}
