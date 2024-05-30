package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.entity.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestConverter {
    public Request toEntity(RequestDTO dto){
        if (dto==null) return null;

        Request request = new Request();
        request.setRequestId(dto.getRequestId());
        request.setRequestDate(dto.getRequestDate());
        request.setStatus(dto.getStatus());
        request.setEvaluationDate(dto.getEvaluationDate());
        request.setPreliminaryPrice(dto.getPreliminaryPrice());
        request.setFinalPrice(dto.getFinalPrice());
        request.setJewelry(dto.getJewelry());
        request.setAccount(dto.getAccount());
        return request;
    }

    public RequestDTO toEntity(Request request){
        if (request==null) return null;

        RequestDTO dto = new RequestDTO();
        dto.setRequestId(request.getRequestId());
        dto.setRequestDate(request.getRequestDate());
        dto.setStatus(request.getStatus());
        dto.setEvaluationDate(request.getEvaluationDate());
        dto.setPreliminaryPrice(request.getPreliminaryPrice());
        dto.setFinalPrice(request.getFinalPrice());
        dto.setJewelry(request.getJewelry());
        dto.setAccount(request.getAccount());
        return dto;
    }
}
