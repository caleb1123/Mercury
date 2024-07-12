package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.converter.AuctionConverter;
import com.g3.Jewelry_Auction_System.converter.BidConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Auction;

import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;

import com.g3.Jewelry_Auction_System.payload.request.WinnerRequest;
import com.g3.Jewelry_Auction_System.payload.response.AuctionToEndResponse;
import com.g3.Jewelry_Auction_System.payload.response.UpcomingAuctionResponse;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AuctionConverter auctionConverter;
    @Autowired
    JewelryRepository JewelryRepository;
    @Autowired
    JewelryRepository jewelryRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    BidConverter bidConverter;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        if (auctionRepository.findById(auctionDTO.getAuctionId()).isPresent()) {
            throw new AppException(ErrorCode.ID_EXISTED);
        }
        LocalDateTime startDate = auctionDTO.getStartDate();
        LocalDateTime endDate = auctionDTO.getEndDate();
        Jewelry jewelry = jewelryRepository
                .findByJewelryId(auctionDTO
                        .getJewelryId()).orElseThrow(() -> new AppException(ErrorCode.JEWELRY_NOT_EXISTED));
        List<Auction> existingAuctions = auctionRepository.findByJewelry(jewelry);
//        if (!startDate.isAfter(LocalDateTime.now().plusDays(1))) {
//            throw new IllegalArgumentException("Start date has to be at least 24h after today");
//        }
        if (startDate.isAfter(endDate)) {
            throw new AppException(ErrorCode.INVALID_STARTDATE);
        }
        if (LocalDateTime.now().isAfter(endDate)) {
            throw new AppException(ErrorCode.INVALID_ENDDATE);
        }
        if (auctionDTO.getCurrentPrice() < 1) {
            throw new AppException(ErrorCode.INVALID_VALUE);
        }
        if (existingAuctions.stream().anyMatch(auction -> "ACTIVE".equals(auction.getStatus())) || !jewelry.getStatus()) {
            throw new AppException(ErrorCode.JEWELRY_NOT_VALID);
        }

        Auction auction = auctionConverter.toEntity(auctionDTO);
        auction.setStatus("Pending");
        auctionRepository.save(auction);
        return auctionConverter.toDTO(auction);
    }
    @Override
    public void deleteAuction(int auctionId) {
        Auction auction = auctionRepository
                .findById(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_FOUND));
        auction.setStatus("Deleted");
        Jewelry jewelry = auction.getJewelry();
        jewelry.setStatus(false);
        jewelryRepository.save(jewelry);
        auctionRepository.save(auction);
    }
    @Override
    public void updateAuction(AuctionDTO auctionDTO, int id) {
//        if (auctionDTO.getAuctionId() != id) {
//            throw new AppException(ErrorCode.ID_NOT_MATCHED);
//        }
        Auction auction = auctionRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_FOUND));
        if(auction.getStatus() == "Pending"){
            if (auctionDTO.getStartDate() != null) {
                if (auctionDTO.getStartDate().isAfter(auctionDTO.getEndDate())) {
                    throw new AppException(ErrorCode.INVALID_STARTDATE);
                } else {
                    auction.setStartDate(auctionDTO.getStartDate());
                }
            }
            if (auctionDTO.getEndDate() != null) {
                if (LocalDateTime.now().isAfter(auctionDTO.getEndDate())) {
                    throw new AppException(ErrorCode.INVALID_ENDDATE);
                } else {
                    auction.setEndDate(auctionDTO.getEndDate());
                }
            }
            auctionRepository.save(auction);
        }else  throw  new AppException(ErrorCode.INVALID_STATUS);
    }
    @Override
    public List<AuctionDTO> getAuctionList() {
        return auctionConverter.toDTO(auctionRepository.findAll());
    }
    @Override
    public List<AuctionDTO> getAuctionByStatus(String status) {
        //List<AuctionDTO> auctionDTOList = getAuctionList();
        //auctionDTOList.removeIf(auctionDTO -> !auctionDTO.getStatus().equals(status.toUpperCase()));
        return auctionConverter.toDTO(auctionRepository.getAuctionByStatus(status));
    }
    @Override
    public List<AuctionDTO> getLiveAuctionList() {
        List<AuctionDTO> auctionDTOList = getAuctionList();
        LocalDateTime now = LocalDateTime.now();
        auctionDTOList.removeIf(auctionDTO
                -> now.isBefore(auctionDTO.getStartDate()) || now.isAfter(auctionDTO.getEndDate()));
        return auctionDTOList;
    }
    @Override
    public List<UpcomingAuctionResponse> getUpcomingAuctionList() {
        List<Object[]> list = auctionRepository.getUpcomingAuctions();
        List<UpcomingAuctionResponse> upcomingAuctionResponseList = new ArrayList<>();
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.LIST_EMPTY);
        } else {
            for (Object[] row : list) {
                upcomingAuctionResponseList.add(new UpcomingAuctionResponse(
                        (int) row[0],
                        (double) row[1],
                        (Timestamp) row[2],
                        (Timestamp) row[3],
                        (String) row[4],
                        (int) row[6],
                        (int) row[7]
                ));
            }
            return upcomingAuctionResponseList;
        }
    }
    @Override
    public BidDTO getHighestBid(int auctionId) {
        auctionRepository.findById(auctionId).orElseThrow(
                () -> new AppException(ErrorCode.AUCTION_NOT_FOUND)
        );
        Bid highestBid = bidRepository
                .getHighestBidAmount(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.BID_NOT_FOUND));
        return bidConverter.toDTO(highestBid);
    }

    @Override
    public void sendEmailToWinner(int auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(
                () -> new AppException(ErrorCode.AUCTION_NOT_FOUND)
        );
                var account =accountRepository.findById(auction.getWinnerId()).orElseThrow(
                        ()-> new AppException(ErrorCode.USER_NOT_EXISTED)
                );
                Bid highestBid = bidRepository
                        .getHighestBidAmount(auctionId)
                        .orElseThrow(() -> new AppException(ErrorCode.BID_NOT_FOUND));
                WinnerRequest response = new WinnerRequest();
                response.setTo(account.getEmail());
                response.setFullname(account.getFullName());
                response.setAuctionName(auction.getJewelry().getJewelryName());
                response.setWinningBid(highestBid.getBidAmount());
                try{
                    emailService.sendAuctionWinnerEmail(response.getTo(), response.getAuctionName(), response.getWinningBid(), response.getFullname());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
    }

    @Override
    public List<AuctionToEndResponse> getAuctionsWithDaysToEnd() {
        List<Object[]> results = auctionRepository.findOngoingAuctionsOrderByDaysToEnd();
        return results.stream().map(result -> AuctionToEndResponse.builder()
                        .auctionId((Integer) result[0])
                        .currentPrice((Double) result[1])
                        .endDate(convertToLocalDateTime((Timestamp) result[2]))
                        .startDate(convertToLocalDateTime((Timestamp) result[3]))
                        .status((String) result[4])
                        .winnerId(result[5] != null ? (Integer) result[6] : null)
                        .jewelryId((Integer) result[6])
                        .daysToEnd((Integer) result[7])
                        .build())
                .collect(Collectors.toList());
    }
    private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    @Override
    public WinnerResponse getWinner(int auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(
                ()-> new AppException(ErrorCode.AUCTION_NOT_FOUND)
        );
        if (auction.getEndDate().isBefore(LocalDateTime.now())) {
            List<Object[]> winnerData = auctionRepository.getWinnerByAuctionId(auctionId);
            if (!winnerData.isEmpty()) {
                Object[] firstWinner = winnerData.get(0);
                return new WinnerResponse(
                        (Integer) firstWinner[0],
                        (String) firstWinner[1],
                        (Double) firstWinner[2],
                        (Integer) firstWinner[3],
                        (String) firstWinner[4],
                        (Integer) firstWinner[5]

                );
            } else {
                throw new AppException(ErrorCode.BID_NOT_FOUND);
            }
        } else {
            throw new AppException(ErrorCode.AUCTION_NOT_CLOSED);
        }
    }

    @Override
    public LocalDateTime getTargetDate(int auctionId) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(auctionRepository.findById(auctionId).get().getStartDate())) {
            return auctionRepository.findById(auctionId).get().getStartDate();
        }
        return auctionRepository.findById(auctionId).get().getEndDate();
    }

    @Override
    public List<AuctionDTO> getWonAuctions() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUserName(name).orElse(null);
        if (name.equals("anonymousUser")) {
            throw new AppException(ErrorCode.NOT_LOGGED_IN);
        } else if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return auctionConverter.toDTO(auctionRepository.getAuctionsByWinnerId(account.getAccountId()));
    }
}
