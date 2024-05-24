package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column
    private String title;

    @Column
    private LocalDate postDate;

    @Column
    private String content;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "postCategoryId")
    private PostCategory postCategory;
}
