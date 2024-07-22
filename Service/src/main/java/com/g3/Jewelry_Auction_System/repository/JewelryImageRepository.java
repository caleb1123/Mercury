package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JewelryImageRepository extends JpaRepository<JewelryImage, Integer> {
    @Query(value = "SELECT * FROM Jewelry_Image where jewelry_id = :id" , nativeQuery = true)
    List<JewelryImage> getByJewelryId(int id);
    JewelryImage findByFileId(String fileId);

    @Query(value = "SELECT TOP 1\n" +
            "    [jewelry_image_id],\n" +
            "    [jewelry_imageurl],\n" +
            "    [jewelry_id],\n" +
            "    [file_id],\n" +
            "    [status]\n" +
            "FROM\n" +
            "    [JewelryAuctionSystem].[dbo].[jewelry_image]\n" +
            "WHERE\n" +
            "    [jewelry_id] = :id and status = 1\n" +
            "ORDER BY\n" +
            "    [jewelry_image_id] ASC;\n" , nativeQuery = true)
    JewelryImage findJewelryImageAuto(int id);

    @Query(value = "SELECT * FROM Jewelry_Image where jewelry_id = :id and status = 1" , nativeQuery = true)
    List<JewelryImage> getByJewelryIdWithoutStatusFalse(int id);

    @Query(value = "SELECT COUNT(jewelry_image_id) AS image_count FROM jewelry_image where jewelry_id = :id and status = 1 GROUP BY jewelry_id ;\n",nativeQuery = true)
    Integer getImageCountByJewelryId(int id);
}
