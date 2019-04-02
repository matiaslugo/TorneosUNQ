package application.controller;

import application.domain.Championship;
import application.repository.ChampionshipRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import javax.validation.Valid;

import java.util.Collection;
import java.util.stream.Collectors;

@Transactional
@RestController
public class ChampionshipController {

    @Autowired
    private ChampionshipRepository repository;

    @GetMapping("/torneos")
    public Collection<Championship> getAll() {
        return (Collection<Championship>) repository.findAll().stream()
                .collect(Collectors.toList());
    }

    @PostMapping("/crearTorneo")
    public Championship createNote(@Valid @RequestBody Championship torneo) {
        return repository.save(torneo);
    }
}
