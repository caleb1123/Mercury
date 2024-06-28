package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserName(String userName);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhone(String phone);

    @Query(value = "Select * from Account", nativeQuery = true)
    List<Account> getAllAccounts();

    @Query(value = "SELECT * FROM account WHERE full_name LIKE '%'+:name+'%'", nativeQuery = true)
    List<Account> searchAccountByName( String name);

    @Query(value ="SELECT a.account_id, a.address, a.email, a.full_name, a.phone, a.sex, a.status, a.role_id, a.user_name FROM account a JOIN role r ON a.role_id = r.role_id WHERE r.role_name = 'USER'\n" ,nativeQuery = true)
    List<Object[]> searchAccountByRoleName(@Param("roleName") String roleName);


}
