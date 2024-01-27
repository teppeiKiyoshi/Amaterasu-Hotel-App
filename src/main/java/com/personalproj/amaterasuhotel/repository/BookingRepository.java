package com.personalproj.amaterasuhotel.repository;

import com.personalproj.amaterasuhotel.model.BookedRoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<BookedRoomModel, Long> {
    BookedRoomModel findByBookingConfirmationCode(String confirmationCode);
    List<BookedRoomModel> findByRoomId(Long roomId);
}
