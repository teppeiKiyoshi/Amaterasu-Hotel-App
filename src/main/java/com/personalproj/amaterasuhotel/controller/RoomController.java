package com.personalproj.amaterasuhotel.controller;

import com.personalproj.amaterasuhotel.exception.PhotoRetrievalException;
import com.personalproj.amaterasuhotel.exception.ResourceNotFoundException;
import com.personalproj.amaterasuhotel.model.BookedRoomModel;
import com.personalproj.amaterasuhotel.model.RoomModel;
import com.personalproj.amaterasuhotel.request.AddRoomRequest;
import com.personalproj.amaterasuhotel.response.BookingResponse;
import com.personalproj.amaterasuhotel.response.RoomResponse;
import com.personalproj.amaterasuhotel.service.BookingService;
import com.personalproj.amaterasuhotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/amaterasu/v1/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("roomType") String roomType, @RequestParam("roomPrice") BigDecimal roomPrice, @RequestParam("photo") MultipartFile photo) throws SQLException, IOException {
        RoomModel savedRoom = roomService.addNewRoom(roomType, roomPrice, photo);
        RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }


    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException, ResourceNotFoundException {
        List<RoomModel> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(RoomModel room : rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    private RoomResponse getRoomResponse(RoomModel room) {
        List<BookedRoomModel> bookings = getAllBookingsByRoomId(room.getId());
        List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(
                        booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode())
                ).toList();

        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if(photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException exception){
                throw new PhotoRetrievalException("Sorry, there was an error retrieving the photo.");
            }
        }


        return new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice(), room.isBooked(), photoBytes, bookingInfo);
    }

    private List<BookedRoomModel> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }

}
