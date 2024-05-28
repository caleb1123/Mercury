package converter;

import com.g3.Jewelry_Auction_System.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.entity.Bid;
import org.springframework.stereotype.Component;

@Component
public class BidConverter {
    public Bid toEntity(BidDTO bidDTO) {
        if (bidDTO == null) return null;
        Bid bid = new Bid();
        bid.setBidId(bidDTO.getBidId());
        bid.setBidAmount(bidDTO.getBidAmount());
        bid.setBidTime(bidDTO.getBidTime());
        bid.setAuction(bidDTO.getAuction());
        bid.setAccount(bidDTO.getAccount());
        return bid;
    }

    public BidDTO toDTO(Bid bid) {
        if (bid == null) return null;
        BidDTO dto = new BidDTO();
        dto.setBidId(bid.getBidId());
        dto.setBidAmount(bid.getBidAmount());
        dto.setBidTime(bid.getBidTime());
        dto.setAuction(bid.getAuction());
        dto.setAccount(bid.getAccount());
        return dto;
    }
}
