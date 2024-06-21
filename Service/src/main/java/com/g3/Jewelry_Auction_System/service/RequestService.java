package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;

import java.util.List;

public interface RequestService {
    RequestDTO createRequest(RequestDTO requestDTO);
    void updatePreliminaryPrice(int id, RequestDTO requestDTO);
    void updateFinalPrice(int id, RequestDTO requestDTO);
    void deleteRequest(int requestID);
    List<RequestDTO> getRequestList();
    List<RequestDTO> getRequestByStatus(String status);

}
