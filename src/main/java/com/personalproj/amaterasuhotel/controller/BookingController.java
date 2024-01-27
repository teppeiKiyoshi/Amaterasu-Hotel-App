package com.personalproj.amaterasuhotel.controller;

import com.personalproj.amaterasuhotel.exception.InvalidBookingRequestException;
import com.personalproj.amaterasuhotel.exception.ResourceNotFoundException;
import com.personalproj.amaterasuhotel.model.BookedRoomModel;
import com.personalproj.amaterasuhotel.model.RoomModel;
import com.personalproj.amaterasuhotel.response.BookingResponse;
import com.personalproj.amaterasuhotel.response.RoomResponse;
import com.personalproj.amaterasuhotel.service.BookingService;
import com.personalproj.amaterasuhotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/amaterasu/v1/api/booking")
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedRoomModel> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoomModel booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) throws ResourceNotFoundException {
        BookedRoomModel booking = bookingService.findByBookingConfirmationCode(confirmationCode);
        BookingResponse bookingResponse = getBookingResponse(booking);
        return ResponseEntity.ok(bookingResponse);
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoomModel bookingRequest){
        try {
            String confirmationCode  = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Room booked successfully! Your confirmation code is: " + confirmationCode);
        } catch (InvalidBookingRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedRoomModel booking) {
        RoomModel theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse roomResponse = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(),
                roomResponse
        );
    }
}
