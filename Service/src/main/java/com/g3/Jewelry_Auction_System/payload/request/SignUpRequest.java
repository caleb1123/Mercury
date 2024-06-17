package com.g3.Jewelry_Auction_System.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private int roleId;
}
