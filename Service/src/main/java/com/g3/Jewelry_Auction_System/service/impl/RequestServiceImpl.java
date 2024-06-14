package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.RequestConverter;
import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.repository.RequestRepository;
import com.g3.Jewelry_Auction_System.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestConverter requestConverter;
    @Autowired
    JewelryRepository jewelryRepository;

    @Override
    public RequestDTO createRequest(RequestDTO requestDTO) {
        if (requestRepository.findByRequestId(requestDTO.getRequestId()).isPresent()) {
            throw new AppException(ErrorCode.ID_EXISTED);
        }
        Optional<Request> existingRequest = requestRepository.findByJewelry(jewelryRepository.getReferenceById(requestDTO.getJewelryId()));
        if (existingRequest.isPresent() && existingRequest.get().getStatus()) {
            throw new AppException(ErrorCode.REQUEST_EXISTED);
        }
        Request request = requestConverter.toEntity(requestDTO);
        request.setRequestDate(LocalDate.now());
        requestRepository.save(request);
        return requestConverter.toDTO(request);
    }

    @Override
    public void updatePreliminaryPrice(int id, RequestDTO requestDTO) {
        if (requestDTO.getRequestId() != id) {
            throw new AppException(ErrorCode.ID_NOT_MATCHED);
        }
        Request request = requestRepository
                .findByRequestId(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (requestDTO.getPreliminaryPrice() < 0) {
            throw new IllegalArgumentException("Preliminary price cannot be negative");
        }
        if (request.getPreliminaryPrice() != requestDTO.getPreliminaryPrice()) {
            request.setPreliminaryPrice(requestDTO.getPreliminaryPrice());
            request.setEvaluationDate(LocalDate.now());
            request.setStatus(true);
        }
        requestRepository.save(request);
    }

    @Override
    public void updateFinalPrice(int id, RequestDTO requestDTO) {
        if (requestDTO.getRequestId() != id) {
            throw new AppException(ErrorCode.ID_NOT_MATCHED);
        }
        Request request = requestRepository
                .findByRequestId(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (requestDTO.getFinalPrice() < 0) {
            throw new IllegalArgumentException("Final price cannot be negative");
        }
        if (request.getFinalPrice() != requestDTO.getFinalPrice()) {
            request.setFinalPrice(requestDTO.getFinalPrice());
            request.setEvaluationDate(LocalDate.now());
            request.setStatus(true);
        }
        requestRepository.save(request);
    }

    @Override
    public void deleteRequest (int requestId) {
        Request request = requestRepository
                .findByRequestId(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(false);
        requestRepository.save(request);
    }

    @Override
    public List<RequestDTO> getRequestList() {
        List<Request> requestList = requestRepository.findAll();
        List<RequestDTO> requestDTOList = new ArrayList<>();
        for (Request request : requestList) {
            requestDTOList.add(requestConverter.toDTO(request));
        }
        return requestDTOList;
    }
    @Override
    public List<RequestDTO> getRequestByStatus(boolean status) {
        List<RequestDTO> requestDTOList = getRequestList();
        requestDTOList.removeIf(requestDTO -> requestDTO.getStatus() != status);
        return requestDTOList;
    }
}
