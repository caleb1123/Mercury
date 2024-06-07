package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.RequestConverter;
import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.repository.RequestRepository;
import com.g3.Jewelry_Auction_System.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestConverter requestConverter;

    @Override
    public Request createRequest(RequestDTO requestDTO) {
        Request request = requestConverter.toEntity(requestDTO);
        requestRepository.save(request);
        return request;
    }
}
