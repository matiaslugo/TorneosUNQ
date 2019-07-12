package application.controller;

import application.domain.Championship;
import application.domain.*;
import application.dto.ChampionshipDTO;
import application.dto.FixtureDTO;
import application.dto.GameDTO;
import application.dto.GameFixtureDTO;
import application.repository.ChampionshipRepository;
import application.repository.TeamRepository;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ChampionshipController {

    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private TeamRepository repositoryTeam;

    @Autowired
    private GameController gameController;

    @GetMapping("/championships")
    public Collection<ChampionshipDTO> getAll() {
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
*/
        List<Positions> positions = new ArrayList<Positions>();

        //positions.add(posit);

        //Championship ch = new Championship("TORNEO UNQ","ALTO TORNEO",new DateTime("1977-10-06"),new DateTime("1977-12-06"),positions,new Fixture());

        //repository.save(ch);
        Collection<Championship> championships;
        ArrayList<ChampionshipDTO> championshipsReturn = new ArrayList<ChampionshipDTO>();
        championships = (Collection<Championship>) repository.findAll().stream()
        .collect(Collectors.toList());
        
            for (Championship ch : championships) {
              ChampionshipDTO championship = mapChampionship(ch);
              championshipsReturn.add(championship);
            }

        return championshipsReturn;
    }

    public ChampionshipDTO mapChampionship(Championship championshipToMap)
    {
        ChampionshipDTO newChampionship = new ChampionshipDTO();
        newChampionship.setId((championshipToMap.getId().toString()));
        newChampionship.setName(championshipToMap.getName());
        newChampionship.setDescription(championshipToMap.getDescription());
        newChampionship.setStartDate(championshipToMap.getStartDate().toString().split("T")[0]);
        newChampionship.setFinishDate(championshipToMap.getFinishDate().toString().split("T")[0]);

        return newChampionship;
    }

    @PostMapping(path="/championshipCreate/")
    // @RequestMapping(value="/championshipCreate",method= {RequestMethod.GET,RequestMethod.POST})
    public void championshipCreate(@RequestBody ChampionshipDTO championship) {

        Championship newChampionship = new Championship();

        newChampionship.setName(championship.getName());
        newChampionship.setDescription(championship.getDescription());
        newChampionship.setFixture(new Fixture());
        newChampionship.setStartDate(new DateTime(championship.getStartDate()));
        newChampionship.setFinishDate(new DateTime(championship.getFinishDate()));
        newChampionship.setPositions(new ArrayList<Positions>());

         repository.save(newChampionship);
    }

     @GetMapping("/championshipById/{id}")
    public ChampionshipDTO championshipById(@PathVariable String id) {
        Optional<Championship> championship = repository.findById(Long.parseLong(id));
        ChampionshipDTO championshipDTO = new ChampionshipDTO();
        championshipDTO.setName(championship.get().getName());
        championshipDTO.setDescription(championship.get().getDescription());
        String[] startDate = championship.get().getStartDate().toString().split("T");
        championshipDTO.setStartDate(startDate[0]);
        String[] finishDate = championship.get().getFinishDate().toString().split("T");
        championshipDTO.setFinishDate(finishDate[0]);
        return championshipDTO;
    }

    @PostMapping(path ="/championshipUpdate/{id}")
    public void championshipUpdate(@PathVariable String id, @RequestBody ChampionshipDTO championship) {

        Optional<Championship> championshipById = repository.findById(Long.parseLong(id));
        championshipById.get().setName(championship.getName());
        championshipById.get().setDescription(championship.getDescription());
        championshipById.get().setStartDate(new DateTime(championship.getStartDate()));
        championshipById.get().setFinishDate(new DateTime(championship.getFinishDate()));
        repository.save(championshipById.get());
    }

    @RequestMapping(method=RequestMethod.DELETE, path="/deleteChampionship/{id}")
    public void deleteChampionship(@PathVariable String id) {
        repository.deleteById(Long.parseLong(id));
    }

    @GetMapping("/tablePositions")
    public Collection<StatisticTeam> getStatisticTeam() {

        try {

            Positions positions = repository.findLastChampionship().getPositions().get(0);

            positions.ordenar();

            return (Collection<StatisticTeam>) positions.getStatisticTeams().stream()
                    .collect(Collectors.toList());

        }
        catch(Exception e){
            return new ArrayList<StatisticTeam>();
        }

    }

    @PostMapping(path ="/addTeams/")
    public void addTeams(@RequestBody ArrayList<String> teamsId) {
        ArrayList<Team> addTeamsChampionship = new ArrayList<Team>();
        Championship championship = repository.findLastChampionship();

        for (String teamId : teamsId) {
           Team team = repositoryTeam.findById(Long.parseLong(teamId)).get();
           if (championship.allTeams().contains(team))
           {
            championship.allTeams().remove(team);
           }
            championship.allTeams().add(team);
        }

        repository.save(championship);
    }

    @GetMapping("/matchesNotPlayed")
    public Collection<Game> getGames() {

        Fixture currentFixture = repository.findLastChampionship().getFixture();
 
        /*Collection<Game> matches = (Collection<Game>) currentFixture.getGame().stream()
                .collect(Collectors.toList());

        return matches;*/
        // por ahora se deja traer todos los partidos.
        List<Game> matches = currentFixture.getGame();
        List<Game> matchesWithoutFree = new ArrayList<Game>();

        //Elimino de la lista los partidos donde los equipos quedan libre. Asi no los muestra en el listado.
        for (Game game : matches) {
            if((game.getTeamA().getName() != "Libre") && (game.getTeamB().getName() != "Libre"))
            {
                matchesWithoutFree.add(game);
            }
        }

        List<Game> matchesNotPlayed = new ArrayList<Game>();

        for (int i = 0; i < matchesWithoutFree.size(); ++i) {

            Game matchCurrent = matchesWithoutFree.get(i);
            if(!matchCurrent.isPlayed()){
                matchesNotPlayed.add(matchCurrent);
            }

        }

        return (Collection<Game>) matchesNotPlayed.stream().collect(Collectors.toList());
    }

    @GetMapping("/matchesPlayed")
    public Collection<Game> getGamesPlayed() {

        Fixture currentFixture = repository.findLastChampionship().getFixture();

        List<Game> matches = currentFixture.getGame();
        List<Game> matchesPlayed = new ArrayList<Game>();

        for (int i = 0; i < matches.size(); ++i) {

            Game matchCurrent = matches.get(i);
            if(matchCurrent.isPlayed()){
                matchesPlayed.add(matchCurrent);
            }

        }

        return (Collection<Game>) matchesPlayed.stream().collect(Collectors.toList());
    }


     @GetMapping("/currentMatchWeek")
     public int getFixture() {

         Fixture currentFixture = repository.findLastChampionship().getFixture();

         return currentFixture.getCurrentMatchWeek();
     }


    @GetMapping("/fixtureGenerate")
    public void fixtureGenerate() {

        String idCurrentChampionship = repository.findLastChampionship().getId().toString();
        Championship currentChampionship = repository.findLastChampionship();
        int teamsCount = repositoryTeam.findTeamsByChampionshipId(idCurrentChampionship).size();
        if(teamsCount % 2 != 0)
        {
            setTeamFree(currentChampionship);
        }
        ArrayList<Team> teams = repositoryTeam.findTeamsByChampionshipId(idCurrentChampionship);
        FixtureGenerator fixtureGenerate = new FixtureGenerator();
        Fixture fixtureNew = fixtureGenerate.FixtureCreate(teams);
        currentChampionship.setFixture(fixtureNew);
        repository.save(currentChampionship);
        
    } 

    @GetMapping("/fixture")
    public Collection<FixtureDTO> fixture() {

        Championship currentChampionship = repository.findLastChampionship();
        List<Game> games = (List<Game>) currentChampionship.getFixture().getGame();
        if(games.size() == 0)
        {
            games = new ArrayList<Game>();
        }

        Collections.sort(games);
        List<GameFixtureDTO> gamesDTO = new ArrayList<GameFixtureDTO>();

        for (Game game : games) {
            gamesDTO.add(gameController.mappingGameFixtureDTO(game));
        }
        
        ArrayList<FixtureDTO> fixture = new ArrayList<FixtureDTO>();

        for (int matchDateNumber = 1; matchDateNumber <= currentChampionship.getFixture().matchWeekCount();matchDateNumber++) {
            FixtureDTO newFixtureDTO = new FixtureDTO(matchDateNumber);

            for (GameFixtureDTO game : gamesDTO) 
            {
                if (matchDateNumber == game.getMatchweek())
                {
                    newFixtureDTO.getGames().add(game);
                }
            }
            fixture.add(newFixtureDTO);
        }
        return fixture;
    }

    public void setTeamFree(Championship currentChampionship)
    {
        Team teamFree = repositoryTeam.findTeamByName("Libre");
        if (teamFree == null)
		{
			Team newTeamFree = new Team();
			newTeamFree.setName("Libre");
            repositoryTeam.save(newTeamFree);
            currentChampionship.allTeams().add(newTeamFree);
		}
        else
        {
            currentChampionship.allTeams().add(teamFree);
        }
    }

}
