package com.example.hotelversion2.Web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;


@Controller
public class AuthController{
     @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // L'utilisateur est déjà connecté, rediriger vers une autre page
            return "redirect:/access-denied";
        }
        // Sinon, afficher la page de connexion
        return "login";
    }
    
    @GetMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
     
        model.addAttribute("error", "You are not allowed to access this page");
        return "errors";
    }
    
    @GetMapping(path="/admin")
    public String getAdminHomepage() {
        return"admin_homepage";
    }
    
}
