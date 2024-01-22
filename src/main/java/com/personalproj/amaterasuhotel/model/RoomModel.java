package com.personalproj.amaterasuhotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "room")
public class RoomModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "price")
    private BigDecimal roomPrice;
    @Column(name = "is_booked")
    private boolean isBooked = false;
    @Column(name = "photo")
    @Lob
    private Blob photo;
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "bookings")
    private List<BookedRoomModel> bookings;

    public RoomModel() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoomModel booking){
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        // generate random string for code
        byte[] array = new byte[10]; // length is bounded by 10
        new Random().nextBytes(array);
        String bookingCode = new String(array, StandardCharsets.UTF_8);
        booking.setBookingConfirmationCode(bookingCode);
    }
}
