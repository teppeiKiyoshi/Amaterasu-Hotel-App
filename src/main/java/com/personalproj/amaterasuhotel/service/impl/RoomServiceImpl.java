package com.personalproj.amaterasuhotel.service.impl;

import com.personalproj.amaterasuhotel.exception.InternalServerException;
import com.personalproj.amaterasuhotel.exception.ResourceNotFoundException;
import com.personalproj.amaterasuhotel.model.RoomModel;
import com.personalproj.amaterasuhotel.repository.RoomRepository;
import com.personalproj.amaterasuhotel.request.AddRoomRequest;
import com.personalproj.amaterasuhotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomModel addNewRoom(String roomType, BigDecimal roomPrice, MultipartFile photoFile) throws SQLException, IOException {
        RoomModel room = new RoomModel();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        if(!photoFile.isEmpty()){
            byte[] photoBytes = photoFile.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }

        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<RoomModel> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException, ResourceNotFoundException {
        Optional<RoomModel> theRoom = roomRepository.findById(roomId);
        if(theRoom.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Room not found.");
        }

        Blob photoBlob = theRoom.get().getPhoto();
        if(photoBlob != null){
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }

        return null;
    }

    @Override
    public void deleteRoomByID(Long roomId) {
        Optional<RoomModel> room = roomRepository.findById(roomId);
        if(room.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public RoomModel updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) throws ResourceNotFoundException {
        RoomModel room = roomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("Room not found."));

        if(roomType != null){
            room.setRoomType(roomType);
        }

        if(roomPrice != null) {
            room.setRoomPrice(roomPrice);
        }

        if(photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            }catch (SQLException exception){
                throw new InternalServerException("Error updating room.");
            }
        }

        return roomRepository.save(room);
    }

    @Override
    public Optional<RoomModel> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }
}
