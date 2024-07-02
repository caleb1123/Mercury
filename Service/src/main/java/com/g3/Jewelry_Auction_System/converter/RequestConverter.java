package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.ERequestStatus;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestConverter {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    JewelryConverter jewelryConverter;
    @Autowired
    JewelryRepository jewelryRepository;
    public Request toEntity(RequestDTO dto){
        if (dto==null) return null;
        Request request = new Request();
        request.setRequestId(dto.getRequestId());
        request.setRequestDate(dto.getRequestDate());
        request.setStatus(ERequestStatus.valueOf(dto.getStatus()));
        request.setDeliveryDate(dto.getDeliveryDate());
        request.setEvaluationDate(dto.getEvaluationDate());
        request.setPreliminaryPrice(dto.getPreliminaryPrice());
        request.setFinalPrice(dto.getFinalPrice());
        request.setJewelry(jewelryRepository.getReferenceById(dto.getJewelryId()));
        request.setAccount(accountRepository.getReferenceById(dto.getSellerId()));
        return request;
    }

    public RequestDTO toDTO(Request request){
        if (request==null) return null;
        RequestDTO dto = new RequestDTO();
        dto.setRequestId(request.getRequestId());
        dto.setRequestDate(request.getRequestDate());
        dto.setStatus(request.getStatus().name());
        dto.setDeliveryDate(request.getDeliveryDate());
        dto.setEvaluationDate(request.getEvaluationDate());
        dto.setPreliminaryPrice(request.getPreliminaryPrice());
        dto.setFinalPrice(request.getFinalPrice());
        dto.setJewelryId(jewelryConverter.toDTO(request.getJewelry()).getJewelryId());
        dto.setSellerId(accountConverter.toDTO(request.getAccount()).getAccountId());
        return dto;
    }
    public List<RequestDTO> toDTOList(List<Request> requests){
        List<RequestDTO> dtoList = new ArrayList<>();
        for (Request request : requests){
            dtoList.add(toDTO(request));
        }
        return dtoList;
    }
}
