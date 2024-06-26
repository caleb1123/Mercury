package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column
    @NotBlank(message = "Title is required")
    private String title;

    @Column
    @NotNull(message = "Post date is required")
    private LocalDate postDate;

    @Column
    @NotBlank(message = "Content is required")
    private String content;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "accountId")
    @NotNull(message = "Account is required")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "postCategoryId")
    @NotNull(message = "Post category is required")
    private PostCategory postCategory;
}
