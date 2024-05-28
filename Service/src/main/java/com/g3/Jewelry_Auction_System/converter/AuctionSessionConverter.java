package converter;

import com.g3.Jewelry_Auction_System.DTO.AuctionSessionDTO;
import com.g3.Jewelry_Auction_System.entity.AuctionSession;

public class AuctionSessionConverter {
    public AuctionSession toEntity(AuctionSessionDTO auctionSessionDTO){
        if(auctionSessionDTO == null) return null;
        AuctionSession auctionSession = new AuctionSession();
        auctionSession.setAuctionSessionId(auctionSessionDTO.getAuctionSessionId());
        auctionSession.setStartTime(auctionSessionDTO.getStartTime());
        auctionSession.setEndTime(auctionSessionDTO.getEndTime());
        auctionSession.setStatus(auctionSessionDTO.getStatus());
        auctionSession.setAuction(auctionSessionDTO.getAuction());
        auctionSession.setAccount(auctionSessionDTO.getAccount());
        return auctionSession;
    }
    public AuctionSessionDTO toDTO(AuctionSession auctionSession){
        if (auctionSession == null) return null;
        AuctionSessionDTO dto = new AuctionSessionDTO();
        dto.setAuctionSessionId(auctionSession.getAuctionSessionId());
        dto.setStartTime(auctionSession.getStartTime());
        dto.setEndTime(auctionSession.getEndTime());
        dto.setStatus(auctionSession.getStatus());
        dto.setAuction(auctionSession.getAuction());
        dto.setAccount(auctionSession.getAccount());
        return dto;
    }
}
