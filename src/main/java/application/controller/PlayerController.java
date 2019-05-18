package application.controller;

import application.domain.Player;
import application.dto.PlayerDTO;
import application.repository.PlayerRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Optional;


@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE})
public class PlayerController {

    @Autowired
    private PlayerRepository repository;

    @GetMapping("/jugadores")
    public Collection<Player> getAll() throws IOException, InvalidFormatException {

        return (Collection<Player>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/uploadPhoto/{id}",method= {RequestMethod.GET,RequestMethod.POST})
    public String singleFileUpload(@RequestParam("file") MultipartFile file,@PathVariable String id,
                                   RedirectAttributes redirectAttributes) {

        Player player = repository.findById(Long.parseLong(id)).get();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por Favor seleccione un archivo para cargar");
            return "redirect:uploadStatus";
        }

        try{
            file.getBytes();
            player.setPhoto(file.getBytes());
            repository.save(player);
            redirectAttributes.addFlashAttribute("message",
                    "Cargado exitosamente '" + file.getName() + "'");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    @GetMapping("/playerById/{id}")
    public PlayerDTO playerById(@PathVariable String id) {
        Optional<Player> player = repository.findById(Long.parseLong(id));
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.get().getName());
        playerDTO.setLastName(player.get().getLastName());
        playerDTO.setDni(player.get().getDni());
        String[] parts = player.get().getBirthdate().toString().split("T");
        playerDTO.setBirthdate(parts[0]);
        return playerDTO;
    }

    @PostMapping(path ="/playerUpdate/{id}")
    public void playerUpdate(@PathVariable String id, @RequestBody PlayerDTO player) {

        Optional<Player> playerById = repository.findById(Long.parseLong(id));
        playerById.get().setName(player.getName());
        playerById.get().setLastName(player.getLastName());
        playerById.get().setDni(player.getDni());
        //System.out.println("player.getBirthdate()" + player.getBirthdate());
        playerById.get().setBirthdate(new DateTime(player.getBirthdate()));
        repository.save(playerById.get());
    }

    @RequestMapping(method=RequestMethod.DELETE, path="/deletePlayer/{id}")
    public void deletePlayer(@PathVariable String id) {
        repository.deleteById(Long.parseLong(id));
    }

}
