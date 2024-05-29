package com.g3.Jewelry_Auction_System.payload.DTO;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDTO {
    private int requestId;
    private LocalDate requestDate;
    private Boolean status;
    private LocalDate evaluationDate;
    private double preliminaryPrice;
    private double finalPrice;
    private Jewelry jewelry;
    private Account account;
}
