package com.example.hotelversion2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/clientpage","/pagehome/contact","/about","/booking", "/webjars/**",
                                "/images/**","/login","/css/**","/client/filter")
                        .permitAll()
                        .anyRequest().authenticated() // Tous les endpoints nécessitent une
                                                      // authentification
                )
                //.formLogin(Customizer.withDefaults()); // Active le formulaire de login par défaut
                .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
                //.defaultSuccessUrl("/home", true) // Rediriger vers home après login
                 .successHandler((request, response, authentication) -> {
                        // Si une URL sauvegardée existe (SavedRequest), y rediriger,
                        // sinon rediriger vers home "/"
                        var savedRequest = (org.springframework.security.web.savedrequest.DefaultSavedRequest) request
                                        .getSession()
                                        .getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                        if (savedRequest != null) {
                                response.sendRedirect(savedRequest.getRequestURL());
                        } else {
                                response.sendRedirect("/admin"); // URL par défaut si
                                                            // aucune URL
                                                            // sauvegardée n'existe
                        }
                }) )  
                 .logout((logout) -> logout.permitAll()
                 .logoutSuccessUrl("/") );// Redirige vers la page d'accueil après le logout
                // Optionnel : supprime le cookie de session)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        
        UserDetails user = User.withUsername("ranimkacem5@gmail.com")
                .password(
                    

                        "{bcrypt}$2b$12$Mt3hE30PP8gs8YCDK6.bgeMJQKzDsQuLXrPPAn8Ued/szfvDIeVMa")
                // password: "ranim"
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}