package com.travel.service;

import com.travel.entity.Voyage;
import com.travel.repository.VoyageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VoyageService {
    private final VoyageRepository repo;
    public VoyageService(VoyageRepository repo) { this.repo = repo; }

    /** Recherche publique : uniquement les voyages qui n'ont pas encore quitté. */
    public List<Voyage> rechercher(String destination, LocalDate date) {
        String dest = (destination == null || destination.isBlank()) ? null : destination.trim();
        LocalDateTime min = LocalDateTime.now();
        if (date != null && date.atStartOfDay().isAfter(min)) min = date.atStartOfDay();
        return repo.rechercher(dest, min);
    }
    /** Liste admin : tous les voyages, passés compris. */
    public List<Voyage> tous() { return repo.findAll(Sort.by("dateDepart")); }

    public Voyage trouver(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voyage introuvable"));
    }

    public Voyage ajouter(Voyage v) {
        verifierDate(v.getDateDepart());
        v.setId(null);
        return repo.save(v);
    }

    public Voyage modifier(Long id, Voyage v) {
        Voyage existant = trouver(id);
        // On ne revérifie la date que si elle a changé (permet de corriger un voyage déjà parti).
        if (!minutes(existant.getDateDepart()).equals(minutes(v.getDateDepart()))) verifierDate(v.getDateDepart());
        v.setId(id);
        return repo.save(v);
    }

    public void supprimer(Long id) { repo.delete(trouver(id)); }

    private LocalDateTime minutes(LocalDateTime d) { return d.truncatedTo(ChronoUnit.MINUTES); }

    private void verifierDate(LocalDateTime d) {
        if (d == null || !d.isAfter(LocalDateTime.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La date de départ doit être dans le futur");
    }
}
