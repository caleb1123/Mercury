package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.EJewelCategory;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.service.JewelryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jewelryCategory")
public class JewelryCategoryController {
    @Autowired
    private JewelryCategoryService jewelryCategoryService;
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<JewelryCategoryDTO>> searchJewelryCategory(@PathVariable String searchString) {
        List<JewelryCategoryDTO> resultList = jewelryCategoryService.searchJewelryCategory(searchString);
        if (resultList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
    @GetMapping
    public List<JewelryCategoryDTO> getAllJewelryCategories() {
        return Arrays.stream(EJewelCategory.values())
                .map(category -> new JewelryCategoryDTO(category.getId(), category.getDisplayName()))
                .collect(Collectors.toList());
    }
}
