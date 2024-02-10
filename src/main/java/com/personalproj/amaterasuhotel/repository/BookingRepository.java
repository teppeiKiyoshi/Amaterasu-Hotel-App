package com.personalproj.amaterasuhotel.repository;

import com.personalproj.amaterasuhotel.model.BookedRoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<BookedRoomModel, Long> {
    Optional <BookedRoomModel> findByBookingConfirmationCode(String confirmationCode);
    List<BookedRoomModel> findByRoomId(Long roomId);

    List<BookedRoomModel> findByGuestEmail(String email);
}
