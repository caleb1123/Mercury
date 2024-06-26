package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.RequestConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.ERequestStatus;
import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.repository.RequestRepository;
import com.g3.Jewelry_Auction_System.service.RequestService;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestConverter requestConverter;
    @Autowired
    JewelryRepository jewelryRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    EmailService emailService;

    @Override
    public RequestDTO createRequest(RequestDTO requestDTO) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        if (name.equals("anonymousUser")) {
            throw new AppException(ErrorCode.NOT_LOGGED_IN);
        } else {
            Account account = accountRepository
                    .findByUserName(name)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            requestDTO.setSellerId(account.getAccountId());
            requestDTO.setRequestDate(LocalDate.now());
            requestDTO.setEvaluationDate(null);
            requestDTO.setDeliveryDate(null);
            requestDTO.setStatus("PENDING");
            requestDTO.setPreliminaryPrice(0);
            requestDTO.setFinalPrice(0);
            List<Request> existingRequests = requestRepository.findByJewelryId(requestDTO.getJewelryId());
            if (!existingRequests.isEmpty()
                    && !existingRequests.stream().allMatch(r -> r.getStatus() == ERequestStatus.CANCELED) ) {
                throw new AppException(ErrorCode.REQUEST_EXISTED);
            }
            Request request = requestConverter.toEntity(requestDTO);
            requestRepository.save(request);
            return requestConverter.toDTO(request);
        }
    }

    @Override
    public void updatePreliminaryPrice(int id, RequestDTO requestDTO) {
        if (requestDTO.getRequestId() != id) {
            throw new AppException(ErrorCode.ID_NOT_MATCHED);
        }
        Request request = requestRepository
                .findByRequestId(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        if (requestDTO.getPreliminaryPrice() < 1) {
            throw new AppException(ErrorCode.INVALID_VALUE);
        }
        if (request.getPreliminaryPrice() != requestDTO.getPreliminaryPrice()) {
            request.setPreliminaryPrice(requestDTO.getPreliminaryPrice());
            request.setEvaluationDate(LocalDate.now());
            request.setDeliveryDate(LocalDate.now().plusDays(5));
            request.setStatus(ERequestStatus.AWAITING_APPROVAL);
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
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        if (requestDTO.getFinalPrice() < 1) {
            throw new AppException(ErrorCode.INVALID_VALUE);
        }
        if (request.getFinalPrice() != requestDTO.getFinalPrice()) {
            request.setFinalPrice(requestDTO.getFinalPrice());
            request.setEvaluationDate(LocalDate.now());
            request.setStatus(ERequestStatus.AWAITING_APPROVAL);
        }
        requestRepository.save(request);
    }
    @Override
    public void updateRequestStatus(int id, RequestDTO requestDTO) {
        if (requestDTO.getRequestId() != id) {
            throw new AppException(ErrorCode.ID_NOT_MATCHED);
        }
        Request request = requestRepository
                .findByRequestId(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        try {
            ERequestStatus.valueOf(requestDTO.getStatus());
            request.setStatus(ERequestStatus.valueOf(requestDTO.getStatus()));
            requestRepository.save(request);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }
    }
    @Override
    public void deleteRequest (int requestId) {
        Request request = requestRepository
                .findByRequestId(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        request.setStatus(ERequestStatus.CANCELED);
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
    public List<RequestDTO> getRequestByStatus(String status) {
        List<RequestDTO> requestDTOList = getRequestList();
        requestDTOList.removeIf(requestDTO -> !Objects.equals(requestDTO.getStatus(), status));
        return requestDTOList;
    }
    @Override
    public List<RequestDTO> getRequestByToken() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        if (name.equals("anonymousUser")) {
            throw new AppException(ErrorCode.NOT_LOGGED_IN);
        } else {
            Account account = accountRepository
                    .findByUserName(name)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            List<Request> requestList = requestRepository.getRequestsBySellerId(account.getAccountId());
            List<RequestDTO> requestDTOList = new ArrayList<>();
            for (Request request : requestList) {
                requestDTOList.add(requestConverter.toDTO(request));
            }
            if (requestDTOList.isEmpty()) {
                throw new AppException(ErrorCode.LIST_EMPTY);
            }
            return requestDTOList;
        }
    }

    @Override
    public void sendEmailDeadlineRequest(RequestDTO requestDTO) throws MessagingException {
        Account account = accountRepository.findById(requestDTO.getSellerId()).orElseThrow(
                () -> new AppException(ErrorCode.USERNAME_INVALID)
        );
        Jewelry jewelry = jewelryRepository.findByJewelryId(requestDTO.getJewelryId()).orElseThrow(
                () -> new AppException(ErrorCode.JEWELRY_NOT_EXISTED)
        );

        emailService.  sendPreliminaryValuationCompleteEmail(
                account.getEmail(),
                jewelry.getJewelryName(),
                String.valueOf(requestDTO.getPreliminaryPrice()),
                requestDTO.getEvaluationDate(),
                account.getFullName()

        );
    }
}
