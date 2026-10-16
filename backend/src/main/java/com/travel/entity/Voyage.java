package com.travel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class Voyage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String origine;
    @NotBlank private String destination;
    @NotNull private LocalDateTime dateDepart;
    @Positive private double prix;
    @Min(0) private int placesDisponibles;
    private String imageUrl; // chemin relatif, ex. /uploads/xxxx.jpg
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public LocalDateTime getDateDepart() {
		return dateDepart;
	}
	public void setDateDepart(LocalDateTime dateDepart) {
		this.dateDepart = dateDepart;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getPlacesDisponibles() {
		return placesDisponibles;
	}
	public void setPlacesDisponibles(int placesDisponibles) {
		this.placesDisponibles = placesDisponibles;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
}
