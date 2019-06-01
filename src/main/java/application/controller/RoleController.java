package application.controller;

import application.domain.Game;
import application.domain.Role;
import application.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @GetMapping("/allRole")
    public Collection<Role> getAll() {

        /*Role role = new Role();

        role.setName("ADMIN");

        repository.save(role);*/

        return (Collection<Role>) repository.findAll().stream()
                .collect(Collectors.toList());

    }

}
