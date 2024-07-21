package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.AuctionToEndResponse;
import com.g3.Jewelry_Auction_System.payload.response.UpcomingAuctionResponse;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionControllerTest {

    @Mock
    private AuctionService auctionService;

    @InjectMocks
    private AuctionController auctionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuction() {
        AuctionDTO auctionDTO = new AuctionDTO();
        when(auctionService.createAuction(any(AuctionDTO.class))).thenReturn(auctionDTO);

        ResponseEntity<AuctionDTO> response = auctionController.createAuction(auctionDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(auctionDTO, response.getBody());
        verify(auctionService, times(1)).createAuction(auctionDTO);
    }

    @Test
    public void testDeleteAuction() {
        doNothing().when(auctionService).deleteAuction(anyInt());

        ResponseEntity<Auction> response = auctionController.deleteAuction(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(auctionService, times(1)).deleteAuction(1);
    }

    @Test
    public void testUpdateAuction() {
        AuctionDTO auctionDTO = new AuctionDTO();
        doNothing().when(auctionService).updateAuction(any(AuctionDTO.class), anyInt());

        ResponseEntity<Auction> response = auctionController.updateAuction(1, auctionDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(auctionService, times(1)).updateAuction(auctionDTO, 1);
    }

    @Test
    public void testGetAuctionList() {
        List<AuctionDTO> auctionList = Arrays.asList(new AuctionDTO(), new AuctionDTO());
        when(auctionService.getAuctionList()).thenReturn(auctionList);

        ResponseEntity<List<AuctionDTO>> response = auctionController.getAuctionList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionList, response.getBody());
        verify(auctionService, times(1)).getAuctionList();
    }

    @Test
    public void testGetAuctionByStatus() {
        List<AuctionDTO> auctionList = Arrays.asList(new AuctionDTO(), new AuctionDTO());
        when(auctionService.getAuctionByStatus(anyString())).thenReturn(auctionList);

        ResponseEntity<List<AuctionDTO>> response = auctionController.getAuctionByStatus("active");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionList, response.getBody());
        verify(auctionService, times(1)).getAuctionByStatus("active");
    }

    @Test
    public void testGetLiveAuctionList() {
        List<AuctionDTO> auctionList = Arrays.asList(new AuctionDTO(), new AuctionDTO());
        when(auctionService.getLiveAuctionList()).thenReturn(auctionList);

        ResponseEntity<List<AuctionDTO>> response = auctionController.getLiveAuctionList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionList, response.getBody());
        verify(auctionService, times(1)).getLiveAuctionList();
    }

    @Test
    public void testGetUpcomingAuctionList() {
        List<UpcomingAuctionResponse> auctionList = Arrays.asList(new UpcomingAuctionResponse(), new UpcomingAuctionResponse());
        when(auctionService.getUpcomingAuctionList()).thenReturn(auctionList);

        ResponseEntity<List<UpcomingAuctionResponse>> response = auctionController.getUpcomingAuctionList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionList, response.getBody());
        verify(auctionService, times(1)).getUpcomingAuctionList();
    }

    @Test
    public void testGetHighestBid() {
        BidDTO bidDTO = new BidDTO();
        when(auctionService.getHighestBid(anyInt())).thenReturn(bidDTO);

        ResponseEntity<BidDTO> response = auctionController.getHighestBid(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bidDTO, response.getBody());
        verify(auctionService, times(1)).getHighestBid(1);
    }

    @Test
    public void testGetWinner() {
        WinnerResponse winnerResponse = new WinnerResponse();
        when(auctionService.getWinner(anyInt())).thenReturn(winnerResponse);

        ResponseEntity<WinnerResponse> response = auctionController.getWinner(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(winnerResponse, response.getBody());
        verify(auctionService, times(1)).getWinner(1);
    }

    @Test
    public void testGetAuctionsWithDaysToEnd() {
        List<AuctionToEndResponse> auctionList = Arrays.asList(new AuctionToEndResponse(), new AuctionToEndResponse());
        when(auctionService.getAuctionsWithDaysToEnd()).thenReturn(auctionList);

        List<AuctionToEndResponse> response = auctionController.getAuctionsWithDaysToEnd();

        assertEquals(auctionList, response);
        verify(auctionService, times(1)).getAuctionsWithDaysToEnd();
    }

    @Test
    public void testSendEmailToWinner() {
        doNothing().when(auctionService).sendEmailToWinner(anyInt());

        ResponseEntity<Void> response = auctionController.sendEmailToWinner(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(auctionService, times(1)).sendEmailToWinner(1);
    }

    @Test
    public void testGetTargetDate() {
        LocalDateTime date = LocalDateTime.now();
        when(auctionService.getTargetDate(anyInt())).thenReturn(date);

        ResponseEntity<String> response = auctionController.getTargetDate(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(date.format(DateTimeFormatter.ISO_DATE_TIME), response.getBody());
        verify(auctionService, times(1)).getTargetDate(1);
    }

    @Test
    public void testGetWonAuctions() {
        List<AuctionDTO> auctionList = Arrays.asList(new AuctionDTO(), new AuctionDTO());
        when(auctionService.getWonAuctions()).thenReturn(auctionList);

        ResponseEntity<List<AuctionDTO>> response = auctionController.getWonAuctions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionList, response.getBody());
        verify(auctionService, times(1)).getWonAuctions();
    }
}
