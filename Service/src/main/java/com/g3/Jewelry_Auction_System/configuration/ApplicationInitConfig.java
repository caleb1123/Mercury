package com.g3.Jewelry_Auction_System.configuration;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.ERole;
import com.g3.Jewelry_Auction_System.entity.Role;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    RoleRepository roleRepository;
    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository){
        return args -> {
            if(accountRepository.findByUserName("admin").isEmpty()){
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                String encodedPassword = passwordEncoder.encode("admin");

                Account adminAccount = new Account();
                adminAccount.setUserName("admin");
                adminAccount.setPassword(encodedPassword);
                adminAccount.setStatus(true);

                Role adminRole = roleRepository.findRoleByRoleName(ERole.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                adminAccount.setRole(adminRole);

                accountRepository.save(adminAccount);
                log.warn("admin user has been created with defautl password:admin, please change it!!!");
            }
        };
    }
}
