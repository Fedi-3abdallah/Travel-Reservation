package com.travel.service;

import com.travel.entity.*;
import com.travel.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservations;
    private final VoyageRepository voyages;
    private final UserRepository users;

    public ReservationService(ReservationRepository r, VoyageRepository v, UserRepository u) {
        this.reservations = r; this.voyages = v; this.users = u;
    }

    /** req ne sert qu'à lire voyage.id et nombrePlaces ; le reste est fixé par le serveur. */
    @Transactional
    public Reservation reserver(String email, Reservation req) {
        if (req.getVoyage() == null || req.getVoyage().getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voyage manquant");
        if (req.getNombrePlaces() < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de places invalide");

        User user = users.findByEmail(email).orElseThrow();
        Voyage voyage = voyages.findById(req.getVoyage().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voyage introuvable"));
        if (!voyage.getDateDepart().isAfter(LocalDateTime.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce voyage est déjà parti");
        if (voyage.getPlacesDisponibles() < req.getNombrePlaces())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Places insuffisantes");
        if (voyage.getPlacesDisponibles() < req.getNombrePlaces())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Places insuffisantes");

        voyage.setPlacesDisponibles(voyage.getPlacesDisponibles() - req.getNombrePlaces());
        voyages.save(voyage);

        Reservation r = new Reservation(); // nouvel objet : on ignore id/statut/user envoyés par le client
        r.setUser(user);
        r.setVoyage(voyage);
        r.setNombrePlaces(req.getNombrePlaces());
        return reservations.save(r);
    }

    public List<Reservation> mesReservations(String email) {
        return reservations.findByUserEmailOrderByDateReservationDesc(email);
    }
    public List<Reservation> toutes() { return reservations.findAll(); }

    @Transactional
    public Reservation annuler(Long id, String email, boolean admin) {
        Reservation r = reservations.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));
        if (!admin && !r.getUser().getEmail().equals(email))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès refusé");
        if (r.getStatut() == StatutReservation.ANNULEE)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Déjà annulée");

        r.setStatut(StatutReservation.ANNULEE);
        Voyage v = r.getVoyage();
        v.setPlacesDisponibles(v.getPlacesDisponibles() + r.getNombrePlaces());
        voyages.save(v);
        return reservations.save(r);
    }
}
