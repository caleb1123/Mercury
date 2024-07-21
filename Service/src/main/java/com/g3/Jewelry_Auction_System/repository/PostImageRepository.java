package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import com.g3.Jewelry_Auction_System.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
    @Query(value = "SELECT * FROM Post_Image where post_id = :id and status = 1 " , nativeQuery = true)
    List<PostImage> getByPostId(int id);
    PostImage findByFileId(String fileId);
    @Query(value = "select COUNT(post_image_id) as image_count from post_image where post_id = 5 and status = 1 group by post_id",nativeQuery = true)
    Integer getImageCountByPostId(int id);

    @Query(value = "SELECT TOP 1\n" +
            "    [post_image_id],\n" +
            "    [post_imageurl],\n" +
            "    [post_id],\n" +
            "    [file_id],\n" +
            "    [status]\n" +
            "FROM\n" +
            "    [JewelryAuctionSystem].[dbo].[post_image]\n" +
            "WHERE\n" +
            "    [post_id] = :id and status = 1\n" +
            "ORDER BY\n" +
            "    [post_image_id] ASC;\n" , nativeQuery = true)
    PostImage findPostImageAuto(int id);
}

