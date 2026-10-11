package com.travel.repository;
import com.travel.entity.Voyage;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    @Query("""
        select v from Voyage v
        where (:dest is null or lower(v.destination) like lower(concat('%', :dest, '%')))
          and (:date is null or v.dateDepart >= :date)
        order by v.dateDepart
        """)
    List<Voyage> rechercher(@Param("dest") String dest, @Param("date") LocalDateTime date);
}
