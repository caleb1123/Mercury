package converter;

import com.g3.Jewelry_Auction_System.payload.DTO.PaymentDTO;
import com.g3.Jewelry_Auction_System.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {
    public Payment toEntity(PaymentDTO dto){
        if (dto==null) return null;

        Payment entity = new Payment();
        entity.setPaymentId(dto.getPaymentId());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setAmount(dto.getAmount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setAddress(dto.getAddress());
        entity.setAuction(dto.getAuction());
        entity.setAccount(dto.getAccount());
        return entity;
    }

    public PaymentDTO toDTO(Payment entity){
        if (entity==null) return null;

        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(entity.getPaymentId());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setAmount(entity.getAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setAddress(entity.getAddress());
        dto.setAuction(entity.getAuction());
        dto.setAccount(entity.getAccount());
        return dto;
    }
}
