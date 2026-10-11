package com.travel;

import com.travel.entity.*;
import com.travel.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;

@SpringBootApplication
public class TravelApplication {
    public static void main(String[] args) { SpringApplication.run(TravelApplication.class, args); }

    @Bean
    CommandLineRunner init(UserRepository users, VoyageRepository voyages, PasswordEncoder enc) {
        return args -> {
            if (users.count() == 0) {
                User admin = new User();
                admin.setNom("Admin"); admin.setPrenom("Site");
                admin.setEmail("admin@travel.com"); admin.setPassword(enc.encode("admin123"));
                admin.setRole(Role.ADMIN);
                users.save(admin);
            }
            if (voyages.count() == 0) {
                voyages.save(voyage("Tunis", "Paris", 5, 320, 40));
                voyages.save(voyage("Tunis", "Rome", 10, 280, 30));
                voyages.save(voyage("Tunis", "Istanbul", 15, 450, 25));
            }
        };
    }
    private Voyage voyage(String o, String d, int jours, double prix, int places) {
        Voyage v = new Voyage();
        v.setOrigine(o); v.setDestination(d);
        v.setDateDepart(LocalDateTime.now().plusDays(jours));
        v.setPrix(prix); v.setPlacesDisponibles(places);
        return v;
    }
}
