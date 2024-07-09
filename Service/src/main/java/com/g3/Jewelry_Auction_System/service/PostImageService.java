package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.PostImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostImageService {

    String uploadImageToCloudinary(MultipartFile file, int id) throws IOException;
    boolean deleteImage(String fileId) throws IOException;
    List<PostImageDTO> getImagesByPostId(int id);

    PostImageDTO getImageByPostId(int fileId);

    PostImageDTO getImageAuto(int id);
}
