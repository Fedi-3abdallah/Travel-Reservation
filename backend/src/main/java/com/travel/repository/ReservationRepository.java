package com.example.travel.repository;
import com.example.travel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserEmailOrderByDateReservationDesc(String email);
}
