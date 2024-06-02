package com.g3.Jewelry_Auction_System.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    private int accountId;
    private String fullName;
    private String userName;
    private String address;
    private LocalDate dob;
    private String email;
    private Boolean sex;
    private String phone;
    private Boolean status;
    private int roleId;
}
