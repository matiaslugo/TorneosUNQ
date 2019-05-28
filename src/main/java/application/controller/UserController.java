package application.controller;

import application.domain.User;
import application.dto.PlayerDTO;
import application.dto.UserDTO;
import application.service.SecurityService;
import application.service.UserService;
import application.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestBody UserDTO userForm, BindingResult bindingResult) {

        User newUser = new User();
        newUser.setUsername(userForm.getUsername());
        newUser.setPassword(userForm.getPassword());
        newUser.setPasswordConfirm(userForm.getPasswordConfirm());

        userValidator.validate(newUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(newUser);

        //User userSet = userService.findByUsername(userForm.getUsername());

        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

        return "redirect:/";
    }

    @PostMapping("/login")
    //public String login(Model model, String error, String logout) {
    public String login(@RequestBody UserDTO userForm){
        /*if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");*/
        System.out.println("login");
        System.out.println("userForm.getUsername()");
        System.out.println(userForm.getUsername());

        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/profile")
    public String myProfile(Model model){
        try{
            User user = userService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            model.addAttribute("user",user);
            return "account/user";
            //return user.getUsername();
        } catch (Exception e) {
            return"/error";
        }
    }

}
