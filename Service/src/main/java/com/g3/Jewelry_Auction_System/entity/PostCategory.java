package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "PostCategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private EPostCategory categoryName;

    @OneToMany(mappedBy = "postCategory")
    private Collection<Post> posts;
}
