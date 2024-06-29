package com.g3.Jewelry_Auction_System.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IncrementResponse {
    int id;
    int increment;
}
