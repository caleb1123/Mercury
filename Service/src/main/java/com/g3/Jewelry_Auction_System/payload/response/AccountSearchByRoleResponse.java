package com.g3.Jewelry_Auction_System.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountSearchByRoleResponse {
    int accountId;
    String fullName;
    String address;
    LocalDate dob;
    String email;
    Boolean sex;
    String phone;
    Boolean status;
    int roleId;
    String userName;
}
