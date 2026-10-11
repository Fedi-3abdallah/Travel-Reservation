package com.travel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity @Getter @Setter
public class Voyage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String origine;
    @NotBlank private String destination;
    @NotNull private LocalDateTime dateDepart;
    @Positive private double prix;
    @Min(1) private int placesDisponibles;
    private String imageUrl; 
}
