package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

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

    @Lob
    @Column(columnDefinition = "nvarchar(max)")
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

    @OneToMany(mappedBy = "post")
    private Collection<PostImage> postImages;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + postId +
                ", title='" + title + '\'' +
                // Assuming 'author' is a simple field; adjust based on your entity structure
                ", author='" + account + '\'' +
                // Do not include collections or related entities that could cause recursion
                '}';
    }
}
