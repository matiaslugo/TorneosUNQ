package application.controller;

import application.domain.Player;
import application.repository.PlayerRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class PlayerController {

    @Autowired
    private PlayerRepository repository;

    @GetMapping("/jugadores")
    public Collection<Player> getAll() throws IOException, InvalidFormatException {

        return (Collection<Player>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

//    @PostMapping("/upload/image/{id}") // //new annotation since 4.3
    @RequestMapping(value="/uploadPhoto/{id}",method= {RequestMethod.GET,RequestMethod.POST})
    public String singleFileUpload(@RequestParam("file") MultipartFile file,@PathVariable String id,
                                   RedirectAttributes redirectAttributes) {

        Player player = repository.findById(Long.parseLong(id)).get();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por Favor seleccione un archivo para cargar");
            return "redirect:uploadStatus";
        }

        try{

            /*byte[] picInBytes = new byte[(int) file.getSize()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(picInBytes);
            fileInputStream.close();*/
            file.getBytes();
            player.setPhoto(file.getBytes());
            repository.save(player);
            redirectAttributes.addFlashAttribute("message",
                    "Cargado exitosamente '" + file.getName() + "'");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /*try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");


            String PLAYER_XLSX_FILE_PATH = "./jugadores-xlsx-file.xlsx";

            Player player = new Player();

            List<Player> players = player.setPlayerFromExcel(UPLOADED_FOLDER + file.getOriginalFilename());

            for (int i = 0; i < players.size(); ++i) {

                repository.save(players.get(i));
            }
*/
        /* catch (IOException e) {
            e.printStackTrace();
        }
        catch (InvalidFormatException e) {
            e.printStackTrace();
        }*/

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}
