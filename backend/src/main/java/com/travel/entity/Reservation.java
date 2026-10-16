package com.travel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateReservation = LocalDateTime.now();
    private int nombrePlaces;
    @Enumerated(EnumType.STRING)
    private StatutReservation statut = StatutReservation.CONFIRMEE;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Voyage voyage;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDateReservation() {
		return dateReservation;
	}
	public void setDateReservation(LocalDateTime dateReservation) {
		this.dateReservation = dateReservation;
	}
	public int getNombrePlaces() {
		return nombrePlaces;
	}
	public void setNombrePlaces(int nombrePlaces) {
		this.nombrePlaces = nombrePlaces;
	}
	public StatutReservation getStatut() {
		return statut;
	}
	public void setStatut(StatutReservation statut) {
		this.statut = statut;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Voyage getVoyage() {
		return voyage;
	}
	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
    
}
