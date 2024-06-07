package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.ERole;
import com.g3.Jewelry_Auction_System.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByRoleName(ERole rolename);
}
