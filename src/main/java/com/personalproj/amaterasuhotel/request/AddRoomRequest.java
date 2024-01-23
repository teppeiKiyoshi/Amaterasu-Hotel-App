package com.personalproj.amaterasuhotel.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomRequest {
    String roomType;
    BigDecimal roomPrice;
}
