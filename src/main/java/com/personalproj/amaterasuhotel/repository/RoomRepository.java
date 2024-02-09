package com.personalproj.amaterasuhotel.repository;

import com.personalproj.amaterasuhotel.model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface RoomRepository extends JpaRepository<RoomModel, Long> {

    @Query("SELECT DISTINCT r.roomType FROM RoomModel r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r FROM RoomModel r " +
            "WHERE r.roomType LIKE %:roomType% " +
            "AND r.id NOT IN ( " +
            "  SELECT br.room.id FROM BookedRoomModel br " +
            "  WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
            ")")
    List<RoomModel> findAllRoomsByDatesAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
