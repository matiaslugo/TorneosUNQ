package application.controller;

import application.domain.ExcelToBD;
import application.domain.Player;
import application.domain.Team;
import application.dto.PlayerDTO;
import application.repository.TeamRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private static String UPLOADED_FOLDER = "/application/excel/"; //"/home/victorqac/Documentos/Programming/TIP/TorneosUNQ-Backend/src/main/java/application/excel/";

    //private static String UPLOADED_FOLDER = "/home/victorqac/Documentos/Programming/TIP/TorneosUNQ-Backend/src/main/java/application/excel/";


    @Autowired
    private TeamRepository repository;


    @GetMapping("/teamBy/{id}")
    public  Collection<Player> TeamBy(@PathVariable String id) {

         return (repository.findById(Long.parseLong(id)).get().playersAll()).stream().collect(Collectors.toList());
    }

    @GetMapping("/teams")
    public Collection<Team> getAll() {
        Team freeTeam = repository.findTeamByName("Libre");
        Collection<Team> teamsWithouFree = (Collection<Team>) repository.findAll();
        teamsWithouFree.remove(freeTeam);
        return teamsWithouFree;
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
            // Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

            ExcelToBD excelToBD = new ExcelToBD();

            String fileName = file.getOriginalFilename();
            String[] parts = fileName.split(".xlsx");

            // List<Player> players = excelToBD.setPlayerFromExcel(UPLOADED_FOLDER + file.getOriginalFilename());
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

    @GetMapping("/teamsNotAssigned")
    public Collection<Team> teamsNotAssigned() {
        
        return (Collection<Team>)  repository.findAllTeamsNotAssigned();
    }
}
