package com.g3.Jewelry_Auction_System.service;

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
import java.util.*;

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
    public void testCreateAuction_IdExisted() {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(1);
        when(auctionRepository.findById(anyInt())).thenReturn(Optional.of(new Auction()));

        assertThrows(AppException.class, () -> auctionService.createAuction(auctionDTO), "Expected createAuction to throw AppException, but it didn't");
    }
    @Test
    public void testCreateAuction_JewelryNotExisted() {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(1);
        auctionDTO.setJewelryId(1);
        auctionDTO.setStartDate(LocalDateTime.now().plusDays(2));
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(3));
        auctionDTO.setCurrentPrice(100);

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jewelryRepository.findByJewelryId(anyInt())).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> auctionService.createAuction(auctionDTO), "Expected createAuction to throw AppException, but it didn't");
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
    public void testCreateAuction_EndDateBeforeStartDate() {
        // Tạo đối tượng DTO với ngày kết thúc trước ngày bắt đầu
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(1);
        auctionDTO.setStartDate(LocalDateTime.now().plusDays(2));
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(1)); // Ngày kết thúc trước ngày bắt đầu
        auctionDTO.setJewelryId(1);
        auctionDTO.setCurrentPrice(100);

        // Tạo đối tượng Jewelry với trạng thái hợp lệ
        Jewelry jewelry = new Jewelry();
        jewelry.setStatus(true);

        // Cấu hình mock cho các phương thức gọi tới repository
        when(auctionRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jewelryRepository.findByJewelryId(anyInt())).thenReturn(Optional.of(jewelry));

        // Kiểm tra ngoại lệ được ném ra khi ngày kết thúc trước ngày bắt đầu
        assertThrows(IllegalArgumentException.class, () -> auctionService.createAuction(auctionDTO), "End date has to be at least 5m after start date");
    }


    @Test
    public void testCreateAuction_StartDateInPast() {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(1);
        auctionDTO.setStartDate(LocalDateTime.now().minusDays(1)); // Start date is in the past
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(2));
        auctionDTO.setJewelryId(1);
        auctionDTO.setCurrentPrice(100);

        Jewelry jewelry = new Jewelry();
        jewelry.setStatus(true);

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jewelryRepository.findByJewelryId(anyInt())).thenReturn(Optional.of(jewelry));

        assertThrows(AppException.class, () -> auctionService.createAuction(auctionDTO), "Expected createAuction to throw AppException, but it didn't");
    }
    @Test
    public void testCreateAuction_InvalidPrice() {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(1);
        auctionDTO.setStartDate(LocalDateTime.now().plusDays(2));
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(3));
        auctionDTO.setJewelryId(1);
        auctionDTO.setCurrentPrice(0); // Invalid price

        Jewelry jewelry = new Jewelry();
        jewelry.setStatus(true);

        when(auctionRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jewelryRepository.findByJewelryId(anyInt())).thenReturn(Optional.of(jewelry));

        assertThrows(AppException.class, () -> auctionService.createAuction(auctionDTO), "Expected createAuction to throw AppException, but it didn't");
    }
    @Test
    public void testCreateAuction_JewelryNotValid() {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(1);
        auctionDTO.setStartDate(LocalDateTime.now().plusDays(2));
        auctionDTO.setEndDate(LocalDateTime.now().plusDays(3));
        auctionDTO.setJewelryId(1);
        auctionDTO.setCurrentPrice(100);

        Jewelry jewelry = new Jewelry();
        jewelry.setStatus(true);

        Auction ongoingAuction = new Auction();
        ongoingAuction.setStatus("Ongoing");
        when(auctionRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jewelryRepository.findByJewelryId(anyInt())).thenReturn(Optional.of(jewelry));
        when(auctionRepository.findByJewelry(any(Jewelry.class))).thenReturn(Collections.singletonList(ongoingAuction));

        assertThrows(AppException.class, () -> auctionService.createAuction(auctionDTO), "Expected createAuction to throw AppException, but it didn't");
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
        // Tạo danh sách các phiên đấu giá giả lập
        List<Auction> auctions = new ArrayList<>();
        Auction auction1 = new Auction();
        Auction auction2 = new Auction();
        auctions.add(auction1);
        auctions.add(auction2);

        // Tạo danh sách các phiên đấu giá DTO giả lập
        List<AuctionDTO> auctionDTOs = new ArrayList<>();
        AuctionDTO auctionDTO1 = new AuctionDTO();
        AuctionDTO auctionDTO2 = new AuctionDTO();
        auctionDTOs.add(auctionDTO1);
        auctionDTOs.add(auctionDTO2);

        // Cấu hình mock cho các phương thức gọi tới repository và converter
        when(auctionRepository.getLatestAuctionByStatus(anyString())).thenReturn(auctions);
        when(auctionConverter.toDTO(auctions)).thenReturn(auctionDTOs);

        // Gọi phương thức getAuctionByStatus
        List<AuctionDTO> result = auctionService.getAuctionByStatus("ACTIVE");

        // Đảo ngược danh sách kết quả dự kiến để so sánh
        Collections.reverse(auctionDTOs);

        // Kiểm tra xem danh sách trả về có đúng như mong đợi không
        System.out.println("Expected: " + auctionDTOs);
        System.out.println("Actual: " + result);

        assertEquals(auctionDTOs, result, "The list of AuctionDTOs returned by getAuctionByStatus should be reversed.");
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
    public void testSendEmailToWinner_Success() throws MessagingException {
        int auctionId = 1;
        Auction auction = new Auction();
        auction.setWinnerId(1);
        auction.setJewelry(new Jewelry());
        auction.getJewelry().setJewelryName("Diamond Ring");

        Account account = new Account();
        account.setEmail("winner@example.com");
        account.setFullName("John Doe");

        Bid highestBid = new Bid();
        highestBid.setBidAmount(500);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(bidRepository.getHighestBidAmount(auctionId)).thenReturn(Optional.of(highestBid));
        doNothing().when(emailService).sendAuctionWinnerEmail("winner@example.com", "Diamond Ring", 500, "John Doe");

        assertDoesNotThrow(() -> auctionService.sendEmailToWinner(auctionId));

        verify(emailService).sendAuctionWinnerEmail(
                "winner@example.com",
                "Diamond Ring",
                500,
                "John Doe"
        );
    }

    @Test
    public void testSendEmailToWinner_AuctionNotFound() {
        int auctionId = 1;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> auctionService.sendEmailToWinner(auctionId), "Expected sendEmailToWinner to throw AppException, but it didn't");
    }
    @Test
    public void testSendEmailToWinner_UserNotFound() {
        int auctionId = 1;
        Auction auction = new Auction();
        auction.setWinnerId(1);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(accountRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> auctionService.sendEmailToWinner(auctionId), "Expected sendEmailToWinner to throw AppException, but it didn't");
    }
    @Test
    public void testSendEmailToWinner_BidNotFound() {
        int auctionId = 1;
        Auction auction = new Auction();
        auction.setWinnerId(1);
        auction.setJewelry(new Jewelry());
        auction.getJewelry().setJewelryName("Diamond Ring");

        // Mock trả về Auction nhưng không có Bid
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(accountRepository.findById(auction.getWinnerId())).thenReturn(Optional.of(createTestAccount()));

        // Mock không tìm thấy Bid
        when(bidRepository.getHighestBidAmount(auctionId)).thenReturn(Optional.empty());

        // Kiểm tra ngoại lệ AppException với mã lỗi BID_NOT_FOUND
        AppException thrownException = assertThrows(AppException.class, () -> auctionService.sendEmailToWinner(auctionId));
        assertEquals(ErrorCode.BID_NOT_FOUND, thrownException.getErrorCode());
    }
    private Account createTestAccount() {
        Account account = new Account();
        account.setEmail("email@example.com");
        account.setFullName("John Doe");
        return account;
    }



    @Test
    public void testSendEmailToWinner_EmailSendingFailed() throws MessagingException {
        int auctionId = 1;
        Auction auction = new Auction();
        auction.setWinnerId(1);
        auction.setJewelry(new Jewelry());
        auction.getJewelry().setJewelryName("Diamond Ring");

        Account account = new Account();
        account.setEmail("winner@example.com");
        account.setFullName("John Doe");

        Bid highestBid = new Bid();
        highestBid.setBidAmount(500);

        // Mock các đối tượng
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(bidRepository.getHighestBidAmount(auctionId)).thenReturn(Optional.of(highestBid));
        doThrow(new MessagingException("Email sending failed")).when(emailService).sendAuctionWinnerEmail(anyString(), anyString(), anyDouble(), anyString());

        // Kiểm tra không ném ngoại lệ
        assertDoesNotThrow(() -> auctionService.sendEmailToWinner(auctionId));
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

    @Test
    public void testStopAuction_Success() throws MessagingException {
        int auctionId = 1;

        // Tạo đối tượng Auction và thiết lập trạng thái
        Auction auction = new Auction();
        auction.setStatus("Ongoing");
        auction.setJewelry(new Jewelry());
        auction.getJewelry().setJewelryName("Diamond Ring");

        // Tạo các đối tượng Account và Bid
        Account account1 = new Account();
        account1.setEmail("email1@example.com");
        account1.setFullName("John Doe");

        Account account2 = new Account();
        account2.setEmail("email2@example.com");
        account2.setFullName("Jane Doe");

        Bid bid1 = new Bid();
        bid1.setAccount(account1);

        Bid bid2 = new Bid();
        bid2.setAccount(account2);

        List<Bid> bids = new ArrayList<>();
        bids.add(bid1);
        bids.add(bid2);

        // Mock các phương thức
        when(auctionRepository.findAuctionByAuctionId(auctionId)).thenReturn(auction);
        when(bidRepository.findByAuctionId(auctionId)).thenReturn(bids);

        // Không ném ngoại lệ khi gửi email
        doNothing().when(emailService).sendApologyEmail(anyString(), anyString(), anyString());

        // Thực thi phương thức
        auctionService.stopAuction(auctionId);

        // Xác nhận các hành vi
        verify(auctionRepository).save(auction);
        verify(emailService).sendApologyEmail("email1@example.com", "Diamond Ring", "John Doe");
        verify(emailService).sendApologyEmail("email2@example.com", "Diamond Ring", "Jane Doe");
    }

    @Test
    public void testStopAuction_AuctionNotOngoing() throws MessagingException {
        int auctionId = 1;

        // Tạo đối tượng Auction với trạng thái "Ended"
        Auction auction = new Auction();
        auction.setStatus("Ended");
        auction.setJewelry(new Jewelry());
        auction.getJewelry().setJewelryName("Diamond Ring");

        // Mock phương thức findAuctionByAuctionId
        when(auctionRepository.findAuctionByAuctionId(auctionId)).thenReturn(auction);

        // Thực thi phương thức và kiểm tra ngoại lệ AppException với mã lỗi AUCTION_NOT_ONGOING
        AppException thrownException = assertThrows(AppException.class, () -> auctionService.stopAuction(auctionId));
        assertEquals(ErrorCode.AUCTION_NOT_ONGOING, thrownException.getErrorCode());

        // Xác nhận rằng không gọi phương thức save và không gửi email
        verify(auctionRepository, never()).save(any(Auction.class));
        verify(emailService, never()).sendApologyEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void testStopAuction_AuctionOngoing() throws MessagingException {
        int auctionId = 1;

        // Tạo đối tượng Auction và thiết lập trạng thái là "Ongoing"
        Auction auction = new Auction();
        auction.setStatus("Ongoing");
        auction.setJewelry(new Jewelry());
        auction.getJewelry().setJewelryName("Diamond Ring");

        // Tạo danh sách Bid
        List<Bid> bids = new ArrayList<>();
        Bid bid = new Bid();
        bid.setBidAmount(500);
        Account account = new Account();
        account.setEmail("winner@example.com");
        account.setFullName("John Doe");
        bid.setAccount(account);
        bids.add(bid);

        // Mock phương thức findAuctionByAuctionId
        when(auctionRepository.findAuctionByAuctionId(auctionId)).thenReturn(auction);
        // Mock phương thức findByAuctionId
        when(bidRepository.findByAuctionId(auctionId)).thenReturn(bids);
        // Mock phương thức gửi email
        doNothing().when(emailService).sendApologyEmail(anyString(), anyString(), anyString());

        // Thực thi phương thức
        auctionService.stopAuction(auctionId);

        // Xác nhận rằng phương thức save đã được gọi và email đã được gửi
        verify(auctionRepository, times(1)).save(auction);
        verify(emailService, times(1)).sendApologyEmail(anyString(), anyString(), anyString());
    }
    @Test
    public void testStopAuction_SendEmailFails() throws MessagingException {
        int auctionId = 1;

        // Tạo đối tượng Auction và thiết lập trạng thái
        Auction auction = new Auction();
        auction.setStatus("Ongoing");
        auction.setJewelry(new Jewelry());
        auction.getJewelry().setJewelryName("Diamond Ring");

        // Tạo các đối tượng Account và Bid
        Account account1 = new Account();
        account1.setEmail("email1@example.com");
        account1.setFullName("John Doe");

        Bid bid1 = new Bid();
        bid1.setAccount(account1);

        List<Bid> bids = new ArrayList<>();
        bids.add(bid1);

        // Mock các phương thức
        when(auctionRepository.findAuctionByAuctionId(auctionId)).thenReturn(auction);
        when(bidRepository.findByAuctionId(auctionId)).thenReturn(bids);

        // Ném ngoại lệ khi gửi email
        doThrow(MessagingException.class).when(emailService).sendApologyEmail(anyString(), anyString(), anyString());

        // Thực thi phương thức và không ném ngoại lệ
        assertDoesNotThrow(() -> auctionService.stopAuction(auctionId));

        // Xác nhận các hành vi
        verify(auctionRepository).save(auction);
        verify(emailService).sendApologyEmail("email1@example.com", "Diamond Ring", "John Doe");
    }
}
