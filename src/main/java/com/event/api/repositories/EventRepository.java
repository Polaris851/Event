package com.event.api.repositories;

import com.event.api.model.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.date >= :currentDate")
    public Page<Event> findUpComingEvents(@Param("currentDate")Date currentDate, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "JOIN Address a ON e.id = a.event.id " +
            "WHERE (:city = '' OR a.city LIKE %:city%) " +
            "AND (:uf = '' OR a.uf LIKE %:uf%) " +
            "AND (e.date >= :startDate AND e.date <= :endDate)")
    public Page<Event> findFilteredEvent(@Param("title")String title,
                                         @Param("city")String city,
                                         @Param("uf")String uf,
                                         @Param("startDate")Date startDate,
                                         @Param("endDate")Date endDate,
                                         Pageable pageable);
}
