package com.personalproj.amaterasuhotel.service;

import com.personalproj.amaterasuhotel.model.BookedRoomModel;

import java.util.List;

public interface BookingService  {
    List<BookedRoomModel> getAllBookingsByRoomId(Long roomId);
}
