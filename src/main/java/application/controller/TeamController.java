package application.controller;

import application.domain.Championship;
import application.domain.ExcelToBD;
import application.domain.Player;
import application.domain.Team;
import application.dto.PlayerDTO;
import application.repository.ChampionshipRepository;
import application.repository.TeamRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class TeamController {

    //Save the uploaded file to this folder
    //private static String UPLOADED_FOLDER = "/application/excel/"; //"/home/victorqac/Documentos/Programming/TIP/TorneosUNQ-Backend/src/main/java/application/excel/";

    //private static String UPLOADED_FOLDER = "/home/victorqac/Documentos/Programming/TIP/TorneosUNQ-Backend/src/main/java/application/excel/";


    @Autowired
    private TeamRepository repository;


    @GetMapping("/teamBy/{id}")
    public  Collection<Player> TeamBy(@PathVariable String id) {

        return (repository.findById(Long.parseLong(id)).get().getPlayers()).stream().collect(Collectors.toList());
    }

    @GetMapping("/teams")
    public Collection<Team> getAll() {
        /*Player player1 = new Player("Sergio","Aguero",36158933,new DateTime("1980-12-04"),true);

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

        repository.save(equipoNew);*/

        return (Collection<Team>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @PostMapping("/uploadTeam")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

            ExcelToBD excelToBD = new ExcelToBD();

            String fileName = file.getOriginalFilename();
            String[] parts = fileName.split(".xlsx");

           //List<Player> players = excelToBD.setPlayerFromExcel(UPLOADED_FOLDER + file.getOriginalFilename());
            List<Player> players = excelToBD.setPlayerFromExcel(file.getOriginalFilename());

            Team newTeam = new Team();
            newTeam.setName(parts[0]);
            newTeam.setPlayers(players);

            repository.save(newTeam);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return "ok";
    }

    @PostMapping(path ="/playerCreate/{id}")
    public void playerCreate(@PathVariable String id, @RequestBody PlayerDTO playerDTO) {

        Optional<Team> teamById = repository.findById(Long.parseLong(id));
        Player playerNew = new Player();

        playerNew.setName(playerDTO.getName());
        playerNew.setLastName(playerDTO.getLastName());
        playerNew.setDni(playerDTO.getDni());
        playerNew.setBirthdate(new DateTime(playerDTO.getBirthdate()));

        teamById.get().addPlayer(playerNew);

        repository.save(teamById.get());
    }



}
