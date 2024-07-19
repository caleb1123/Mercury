package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.converter.AuctionConverter;
import com.g3.Jewelry_Auction_System.converter.BidConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.AuctionToEndResponse;
import com.g3.Jewelry_Auction_System.payload.response.UpcomingAuctionResponse;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.service.impl.AuctionServiceImpl;
import com.g3.Jewelry_Auction_System.service.impl.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private AuctionConverter auctionConverter;

    @Mock
    private JewelryRepository jewelryRepository;

    @Mock
    private BidConverter bidConverter;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuctionServiceImpl auctionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuction() {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(1);
        auctionDTO.setStartDate(LocalDateTime.now().plusDays(2));
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(3));
        auctionDTO.setJewelryId(1);
        auctionDTO.setCurrentPrice(100);

        Jewelry jewelry = new Jewelry();
        jewelry.setStatus(true);

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jewelryRepository.findByJewelryId(anyInt())).thenReturn(Optional.of(jewelry));
        when(auctionConverter.toEntity(any(AuctionDTO.class))).thenReturn(new Auction());
        when(auctionConverter.toDTO(any(Auction.class))).thenReturn(auctionDTO);

        AuctionDTO result = auctionService.createAuction(auctionDTO);

        assertEquals(auctionDTO, result);
        verify(auctionRepository, times(1)).save(any(Auction.class));
    }

    @Test
    public void testDeleteAuction() {
        Auction auction = new Auction();
        auction.setStatus("Active");
        Jewelry jewelry = new Jewelry();
        jewelry.setStatus(true);
        auction.setJewelry(jewelry);

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.of(auction));

        auctionService.deleteAuction(1);

        assertEquals("Deleted", auction.getStatus());
        assertFalse(jewelry.getStatus());
        verify(auctionRepository, times(1)).save(auction);
        verify(jewelryRepository, times(1)).save(jewelry);
    }

    @Test
    public void testUpdateAuction() {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setStartDate(LocalDateTime.now().plusDays(2));
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(3));

        Auction auction = new Auction();
        auction.setStatus("Pending");

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.of(auction));

        auctionService.updateAuction(auctionDTO, 1);

        verify(auctionRepository, times(1)).save(auction);
    }

    @Test
    public void testGetAuctionList() {
        List<Auction> auctions = new ArrayList<>();
        List<AuctionDTO> auctionDTOs = new ArrayList<>();

        when(auctionRepository.findAll()).thenReturn(auctions);
        when(auctionConverter.toDTO(auctions)).thenReturn(auctionDTOs);

        List<AuctionDTO> result = auctionService.getAuctionList();

        assertEquals(auctionDTOs, result);
    }

    @Test
    public void testGetAuctionByStatus() {
        List<Auction> auctions = new ArrayList<>();
        List<AuctionDTO> auctionDTOs = new ArrayList<>();

        when(auctionRepository.getAuctionByStatus(anyString())).thenReturn(auctions);
        when(auctionConverter.toDTO(auctions)).thenReturn(auctionDTOs);

        List<AuctionDTO> result = auctionService.getAuctionByStatus("ACTIVE");

        assertEquals(auctionDTOs, result);
    }

    @Test
    public void testGetLiveAuctionList() {
        List<AuctionDTO> auctionDTOList = new ArrayList<>();
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setStartDate(LocalDateTime.now().minusDays(1));
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(1));
        auctionDTOList.add(auctionDTO);

        when(auctionConverter.toDTO(anyList())).thenReturn(auctionDTOList);

        List<AuctionDTO> result = auctionService.getLiveAuctionList();

        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetUpcomingAuctionList() {
        List<Object[]> list = new ArrayList<>();
        Object[] row = new Object[]{1, 100.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), "Pending", 1, 1};
        list.add(row);

        when(auctionRepository.getUpcomingAuctions()).thenReturn(list);

        List<UpcomingAuctionResponse> result = auctionService.getUpcomingAuctionList();

        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetHighestBid() {
        Bid bid = new Bid();
        BidDTO bidDTO = new BidDTO();

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.of(new Auction()));
        when(bidRepository.getHighestBidAmount(anyInt())).thenReturn(Optional.of(bid));
        when(bidConverter.toDTO(bid)).thenReturn(bidDTO);

        BidDTO result = auctionService.getHighestBid(1);

        assertEquals(bidDTO, result);
    }

    @Test
    public void testSendEmailToWinner() throws MessagingException {
        Auction auction = new Auction();
        auction.setWinnerId(1);

        Account account = new Account();
        account.setEmail("test@example.com");
        account.setFullName("Test User");

        Bid highestBid = new Bid();
        highestBid.setBidAmount(100.0);

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.of(auction));
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        when(bidRepository.getHighestBidAmount(anyInt())).thenReturn(Optional.of(highestBid));

        auctionService.sendEmailToWinner(1);

        verify(emailService, times(1)).sendAuctionWinnerEmail(anyString(), anyString(), anyDouble(), anyString());
    }

    @Test
    public void testGetAuctionsWithDaysToEnd() {
        List<Object[]> results = new ArrayList<>();
        Object[] row = new Object[]{1, 100.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), "Pending", 1, 1, 2};
        results.add(row);

        when(auctionRepository.findOngoingAuctionsOrderByDaysToEnd()).thenReturn(results);

        List<AuctionToEndResponse> result = auctionService.getAuctionsWithDaysToEnd();

        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetWinner() {
        Auction auction = new Auction();
        auction.setEndDate(LocalDateTime.now().minusDays(1));

        Object[] winnerData = new Object[]{1, "Test User", 100.0, 1, "Test Auction", 1};
        List<Object[]> winnerDataList = new ArrayList<>();
        winnerDataList.add(winnerData);

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.of(auction));
        when(auctionRepository.getWinnerByAuctionId(anyInt())).thenReturn(winnerDataList);

        WinnerResponse result = auctionService.getWinner(1);

        assertNotNull(result);
    }

    @Test
    public void testGetTargetDate() {
        Auction auction = new Auction();
        auction.setStartDate(LocalDateTime.now().plusDays(1));
        auction.setEndDate(LocalDateTime.now().plusDays(2));

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.of(auction));

        LocalDateTime result = auctionService.getTargetDate(1);

        assertNotNull(result);
    }

    @Test
    public void testGetWonAuctions() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        User user = new User("test", "password", new ArrayList<>());
        when(securityContext.getAuthentication()).thenReturn(new org.springframework.security.core.Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return user;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return user.getUsername();
            }
        });

        Account account = new Account();
        account.setAccountId(1);

        when(accountRepository.findByUserName(anyString())).thenReturn(Optional.of(account));
        when(auctionRepository.getAuctionsByWinnerId(anyInt())).thenReturn(new ArrayList<>());

        List<AuctionDTO> result = auctionService.getWonAuctions();

        assertNotNull(result);
    }
}
