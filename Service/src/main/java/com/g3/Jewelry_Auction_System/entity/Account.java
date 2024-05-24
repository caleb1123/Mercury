package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column
    private String fullName;

    @Column(unique = true,columnDefinition = "varchar(50)")
    private String userName;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    private LocalDate dob;

    @Column(unique = true, columnDefinition = "varchar(50)")
    private String email;

    @Column
    private Boolean sex;

    @Column(unique = true)
    private String phone;

    @Column
    private Boolean status;



    @OneToMany(mappedBy = "account")
    private Collection<Auction> auctions;

    @OneToMany(mappedBy = "account")
    private Collection<Bid> bids;

    @OneToMany(mappedBy = "account")
    private Collection<Payment> payments;

    @OneToMany(mappedBy = "account")
    private Collection<AuctionSession> auctionSessions;

    @OneToMany(mappedBy = "account")
    private Collection<Post> posts;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToMany(mappedBy = "account")
    private Collection<Request> requests;
}
