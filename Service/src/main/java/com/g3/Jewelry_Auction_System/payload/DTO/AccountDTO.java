    package com.g3.Jewelry_Auction_System.payload.DTO;

    import com.g3.Jewelry_Auction_System.entity.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;
    import java.util.Collection;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AccountDTO {
        private int accountId;
        private String fullName;
        private String userName;
        private String password;
        private String address;
        private LocalDate dob;
        private String email;
        private Boolean sex;
        private String phone;
        private Boolean status;
        private int roleId;
    }
