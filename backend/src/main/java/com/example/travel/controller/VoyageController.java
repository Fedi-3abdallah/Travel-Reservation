package com.example.travel.controller;

import com.example.travel.entity.Voyage;
import com.example.travel.service.VoyageService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController @RequestMapping("/api/voyages")
public class VoyageController {
    private final VoyageService service;
    public VoyageController(VoyageService service) { this.service = service; }

    @GetMapping
    public List<Voyage> rechercher(@RequestParam(required = false) String destination,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.rechercher(destination, date);
    }

	//Admin
	@GetMapping("/admin")
	public List<Voyage> tous() { return service.tous(); }
	@GetMapping("/{id}")
	public Voyage details(@PathVariable Long id) { return service.trouver(id); }

	@PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Voyage ajouter(@Valid @RequestBody Voyage v) { return service.ajouter(v); }

    @PutMapping("/{id}")
    public Voyage modifier(@PathVariable Long id, @Valid @RequestBody Voyage v) { return service.modifier(id, v); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) { service.supprimer(id); }
}
