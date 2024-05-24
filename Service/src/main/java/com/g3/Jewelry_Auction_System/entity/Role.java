package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "Role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Collection<Account> accounts;
}
