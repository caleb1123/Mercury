package com.g3.Jewelry_Auction_System.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountRequest {
    private String userName;
    private String email;
    private String phone;
    private int roleId;
}
