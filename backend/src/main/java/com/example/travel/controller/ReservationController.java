package com.example.travel.controller;

import com.example.travel.entity.Reservation;
import com.example.travel.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) { this.service = service; }

	@PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('USER')")
	public Reservation reserver(@RequestBody Reservation req, Authentication auth) {
		return service.reserver(auth.getName(), req);
	}
    @GetMapping("/mes")
    public List<Reservation> mes(Authentication auth) { return service.mesReservations(auth.getName()); }

    @GetMapping @PreAuthorize("hasRole('ADMIN')")
    public List<Reservation> toutes() { return service.toutes(); }

    @DeleteMapping("/{id}")
    public Reservation annuler(@PathVariable Long id, Authentication auth) {
        boolean admin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return service.annuler(id, auth.getName(), admin);
    }
}
