package com.g3.Jewelry_Auction_System.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.g3.Jewelry_Auction_System.converter.PostImageConverter;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import com.g3.Jewelry_Auction_System.entity.Post;
import com.g3.Jewelry_Auction_System.entity.PostImage;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.PostImageDTO;
import com.g3.Jewelry_Auction_System.repository.PostImageRepository;
import com.g3.Jewelry_Auction_System.repository.PostRepository;
import com.g3.Jewelry_Auction_System.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class PostImageServiceImpl implements PostImageService {
    @Autowired
    PostImageRepository postImageRepository;
    @Autowired
    PostImageConverter postImageConverter;
    @Autowired
    PostRepository postRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImageToCloudinary(MultipartFile file, int id) throws IOException {
        Post post = postRepository.getReferenceById(id);
        Integer imageCount = postImageRepository.getImageCountByPostId(id);
        if (imageCount == null) {
            // Xử lý khi imageCount bị null, gán mặc định là 0
            imageCount = 0;
        }
        if (imageCount >= 5) {
            throw new AppException(ErrorCode.IMAGE_MANY);
        }
        if(post != null){
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            PostImage postImage = new PostImage();
            postImage.setPost(post);
            postImage.setPostImageURL((String) uploadResult.get("url"));
            postImage.setStatus(true);
            postImage.setFileId(getRandomNumber(8));
            postImageRepository.save(postImage);
            return (String) uploadResult.get("url");
        }
        return "upload fail";
    }
    private String extractPublicId(String jewelryImageUrl) {
        // Example: https://res.cloudinary.com/demo/image/upload/v1234567890/public_id.jpg
        // Extract public_id "v1234567890/public_id"
        int startIndex = jewelryImageUrl.lastIndexOf("/") + 1;
        int endIndex = jewelryImageUrl.lastIndexOf(".");
        return jewelryImageUrl.substring(startIndex, endIndex);
    }
    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
    @Override
    public boolean deleteImage(String fileId) throws IOException {
        PostImage postImage = postImageRepository.findByFileId(fileId);
        if (postImage != null) {
            String publicId = extractPublicId(postImage.getPostImageURL());
            Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (deleteResult.get("result").equals("ok")) {
                postImage.setStatus(false);
                postImageRepository.save(postImage);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PostImageDTO> getImagesByPostId(int id) {
        List<PostImage> list = postImageRepository.getByPostId(id);
        List<PostImageDTO> dtoList = new ArrayList<>();
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.NO_IMAGE_FOUND);
        } else {
            for (PostImage postImage : list) {
                dtoList.add(postImageConverter.toDTO(postImage));
            }
        }
        return dtoList;
    }

    @Override
    public PostImageDTO getImageByPostId(int id) {
        PostImage image = postImageRepository.findByFileId(String.valueOf(id));
        if(image != null){
            return postImageConverter.toDTO(image);
        }
        return null;
    }

    @Override
    public PostImageDTO getImageAuto(int id) {
        PostImage image = postImageRepository.findPostImageAuto(id);
        return image != null ? postImageConverter.toDTO(image) : null;
    }
}