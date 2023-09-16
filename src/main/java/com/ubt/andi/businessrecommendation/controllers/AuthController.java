package com.ubt.andi.businessrecommendation.controllers;
import com.ubt.andi.businessrecommendation.models.ApplicationUser;
import com.ubt.andi.businessrecommendation.models.Business;
import com.ubt.andi.businessrecommendation.services.ApplicationUserService;
import com.ubt.andi.businessrecommendation.services.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AuthController {

    private ApplicationUserService userService;
    private EmailService emailService;
    @Autowired
    public AuthController(ApplicationUserService userService,EmailService emailService){
        this.userService=userService;
        this.emailService=emailService;
    }
    @GetMapping("/register")
    public String registerForm(Model model){
        ApplicationUser user = new ApplicationUser();
        model.addAttribute("user",user);
        return "register-form";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") ApplicationUser user, BindingResult bindingResult,Model model) throws UnsupportedEncodingException {
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
        String generatedToken = EmailService.generateToken();
        String tokenWithoutHyphens = generatedToken.replace("-","");
        user.setConfirmationToken(tokenWithoutHyphens);
        userService.saveUser(user);
       // emailService.sendEmail(user.getEmail(),tokenWithoutHyphens);
        return "redirect:/business";
    }
    @GetMapping("/login")
    public String loginForm(Model model){
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if(authUser instanceof AnonymousAuthenticationToken){
            model.addAttribute("loggedInUser",new ApplicationUser());
            return "login-form";
        }
        return "redirect:/dashboard";
    }
    @GetMapping("/admin")
    public String adminForm(){
        return "admin-page";
    }
    @GetMapping("/dashboard")
    public String dashboardPage(){
        return "dashboard-page";
    }
    @GetMapping("/confirm")
    public String redirectFromEmail(@RequestParam("token") String token){
        ApplicationUser user = userService.findByConfirmationToken(token);
        if(user != null){
            user.setEmailConfirmed(true);
            userService.updateUser(user);
            return "redirect:/login";
        }
        return "redirect:/register";
    }
    @GetMapping("/profile/edit/{username}")
    public String editProfilePage(@PathVariable("username") String username,Model model){
        ApplicationUser user = userService.findByUsername(username);
        if(user != null){
            model.addAttribute("user",user);
            return "profile-page";
        }
        return "redirect:/dashboard";

    }
    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute("user") ApplicationUser user,@RequestParam("passwordConfirm") String passwordConfirm){
        ApplicationUser userDb = userService.findById(user.getId());
        if(userDb != null){
            if(user.getPassword() == null || user.getPassword().isEmpty()) user.setPassword(userDb.getPassword());
            user.setConfirmationToken(userDb.getConfirmationToken());
            user.setEmailConfirmed(userDb.isEmailConfirmed());
            userService.updateUser(user);
            return "redirect:/dashboard";
        }
        return "redirect:/profile";
    }
}
