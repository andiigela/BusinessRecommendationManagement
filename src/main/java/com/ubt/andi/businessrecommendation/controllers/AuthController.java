package com.ubt.andi.businessrecommendation.controllers;
import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.services.ApplicationUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private ApplicationUserService userService;
    @Autowired
    public AuthController(ApplicationUserService userService){
        this.userService=userService;
    }
    @GetMapping("/register")
    public String registerForm(Model model){
        ApplicationUser user = new ApplicationUser();
        model.addAttribute("user",user);
        return "register-form";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") ApplicationUser user, BindingResult bindingResult,Model model){
        ApplicationUser existingEmail = userService.findByEmail(user.getEmail());
        if(existingEmail != null){
            bindingResult.rejectValue("email", "duplicate.email", "There is already a user with that email");
        }
        ApplicationUser existingUsername = userService.findByUsername(user.getUsername());
        if(existingUsername != null){
            bindingResult.rejectValue("username", "duplicate.username", "There is already a user with that username");        }
        if(bindingResult.hasErrors()){
            model.addAttribute("user",user);
            return "register-form";
        }
        userService.saveUser(user);
        return "redirect:/business";
    }
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loggedInUser",new ApplicationUser());
        return "login-form";
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loggedInUser") ApplicationUser user){
        ApplicationUser username = userService.findByUsername(user.getUsername());
        if(username != null && username.getPassword().equals(user.getPassword())){
            return "welcome-page";
        }
        return "login-form";
    }
    @GetMapping("/admin")
    public String adminForm(){
        return "admin-page";
    }
}
