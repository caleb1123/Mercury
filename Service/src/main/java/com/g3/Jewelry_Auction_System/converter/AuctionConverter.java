package converter;

import com.g3.Jewelry_Auction_System.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.entity.Auction;
import org.springframework.stereotype.Component;

@Component
public class AuctionConverter {
    public Auction toEntity(AuctionDTO auctionDTO) {
        if(auctionDTO == null) return null;

        Auction auction = new Auction();
        auction.setAuctionId(auctionDTO.getAuctionId());
        auction.setStartDate(auctionDTO.getStartDate());
        auction.setEndDate(auctionDTO.getEndDate());
        auction.setCurrentPrice(auctionDTO.getCurrentPrice());
        auction.setStatus(auctionDTO.getStatus());
        auction.setJewelry(auctionDTO.getJewelry());
        auction.setAccount(auctionDTO.getAccount());
        auction.setBids(auctionDTO.getBids());
        auction.setPayments(auctionDTO.getPayments());
        return auction;
    }
    public AuctionDTO toDTO(Auction auction) {
        if(auction == null) return null;

        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(auction.getAuctionId());
        auctionDTO.setStartDate(auction.getStartDate());
        auctionDTO.setEndDate(auction.getEndDate());
        auctionDTO.setCurrentPrice(auction.getCurrentPrice());
        auctionDTO.setStatus(auction.getStatus());
        auctionDTO.setJewelry(auction.getJewelry());
        auctionDTO.setAccount(auction.getAccount());
        auctionDTO.setBids(auction.getBids());
        auctionDTO.setPayments(auction.getPayments());
        return auctionDTO;
    }
}
