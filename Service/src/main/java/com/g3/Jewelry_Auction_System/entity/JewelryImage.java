package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "JewelryImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JewelryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jewelryImageId;

    @Column
    private String fileId;
    @Column
    private String jewelryImageURL;
    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "jewelryId")
    private Jewelry jewelry;

    @Override
    public String toString() {
        return "JewelryImage{" +
                "id=" + jewelryImageId +
                ", jewelryImageURL='" + jewelryImageURL + '\'' +
                ", status=" + status +
                ", fileId='" + fileId + '\'' +
                // Avoid including 'jewelry' here if it has a back-reference to JewelryImage
                '}';
    }
}
