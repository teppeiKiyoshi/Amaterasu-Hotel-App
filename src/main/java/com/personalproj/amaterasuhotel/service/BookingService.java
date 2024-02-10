package com.personalproj.amaterasuhotel.service;

import com.personalproj.amaterasuhotel.exception.ResourceNotFoundException;
import com.personalproj.amaterasuhotel.model.BookedRoomModel;

import java.util.List;

public interface BookingService  {
    List<BookedRoomModel> getAllBookingsByRoomId(Long roomId);

    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookedRoomModel bookingRequest);

    BookedRoomModel findByBookingConfirmationCode(String confirmationCode) throws ResourceNotFoundException;

    List<BookedRoomModel> getAllBookings();

    List<BookedRoomModel> getBookingsByUserEmail(String email);
}
