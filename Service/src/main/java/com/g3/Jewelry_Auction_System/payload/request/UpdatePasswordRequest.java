package com.g3.Jewelry_Auction_System.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordRequest {
    private int accountId;
    private String fullName;
    private String userName;
    private String password;
    private String address;
    private LocalDate dob;
    private String email;
    private Boolean sex;
    private String phone;
    private Boolean status;
    private String resetPasswordToken;
    private int roleId;

    public UpdatePasswordRequest(int accountId) {

    }
}
