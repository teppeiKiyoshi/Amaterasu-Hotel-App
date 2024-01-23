package com.personalproj.amaterasuhotel.service.impl;

import com.personalproj.amaterasuhotel.model.BookedRoomModel;
import com.personalproj.amaterasuhotel.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Override
    public List<BookedRoomModel> getAllBookingsByRoomId(Long roomId) {
        return null;
    }
}
