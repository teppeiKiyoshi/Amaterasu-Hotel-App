package com.personalproj.amaterasuhotel.service;

import com.personalproj.amaterasuhotel.exception.ResourceNotFoundException;
import com.personalproj.amaterasuhotel.model.RoomModel;
import com.personalproj.amaterasuhotel.request.AddRoomRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    RoomModel addNewRoom(String roomType, BigDecimal roomPrice, MultipartFile photoFile) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<RoomModel> getAllRooms();

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException, ResourceNotFoundException;

    void deleteRoomByID(Long roomId);

    RoomModel updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) throws ResourceNotFoundException;

    Optional<RoomModel> getRoomById(Long roomId);
}
