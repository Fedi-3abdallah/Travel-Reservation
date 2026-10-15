package com.travel.repository;
import com.travel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserEmailOrderByDateReservationDesc(String email);
}
