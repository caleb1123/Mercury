package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PostImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postImageId;

    @Column
    private String postImageURL;
    @Column
    private boolean status;
    @Column
    private String fileId;
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
}
