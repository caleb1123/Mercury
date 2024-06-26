package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import jakarta.mail.MessagingException;

import java.util.List;

public interface RequestService {
    RequestDTO createRequest(RequestDTO requestDTO);
    void updatePreliminaryPrice(int id, RequestDTO requestDTO);
    void updateFinalPrice(int id, RequestDTO requestDTO);
    void updateRequestStatus(int id, RequestDTO requestDTO);
    void deleteRequest(int requestID);
    List<RequestDTO> getRequestList();
    List<RequestDTO> getRequestByStatus(String status);
    List<RequestDTO> getRequestByToken();
    void sendEmailDeadlineRequest(RequestDTO requestDTO) throws MessagingException;
    RequestDTO getRequestById(int id);
}
