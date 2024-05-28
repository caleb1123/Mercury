package converter;

import com.g3.Jewelry_Auction_System.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null){
            return null;
        }

        Account entity = new Account();
        entity.setAccountId(accountDTO.getAccountId());
        entity.setFullName(accountDTO.getFullName());
        entity.setUserName(accountDTO.getUserName());
        entity.setPassword(accountDTO.getPassword());
        entity.setAddress(accountDTO.getAddress());
        entity.setDob(accountDTO.getDob());
        entity.setEmail(accountDTO.getEmail());
        entity.setSex(accountDTO.getSex());
        entity.setPhone(accountDTO.getPhone());
        entity.setStatus(accountDTO.getStatus());
        entity.setAuctions(accountDTO.getAuctions());
        entity.setBids(accountDTO.getBids());
        entity.setPayments(accountDTO.getPayments());
        entity.setAuctionSessions(accountDTO.getAuctionSessions());
        entity.setPosts(accountDTO.getPosts());
        entity.setRole(accountDTO.getRole());
        entity.setRequests(accountDTO.getRequests());
        return entity;
    }

    public AccountDTO toDTO(Account account) {
        if (account == null){
            return null;
        }

        AccountDTO dto = new AccountDTO();
        dto.setAccountId(account.getAccountId());
        dto.setFullName(account.getFullName());
        dto.setUserName(account.getUserName());
        dto.setPassword(account.getPassword());
        dto.setAddress(account.getAddress());
        dto.setDob(account.getDob());
        dto.setEmail(account.getEmail());
        dto.setSex(account.getSex());
        dto.setPhone(account.getPhone());
        dto.setStatus(account.getStatus());
        dto.setAuctions(account.getAuctions());
        dto.setBids(account.getBids());
        dto.setPayments(account.getPayments());
        dto.setAuctionSessions(account.getAuctionSessions());
        dto.setPosts(account.getPosts());
        dto.setRole(account.getRole());
        dto.setRequests(account.getRequests());
        return dto;
    }
}
