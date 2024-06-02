package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;

public interface RequestService {
    Request createRequest(RequestDTO requestDTO);
}
