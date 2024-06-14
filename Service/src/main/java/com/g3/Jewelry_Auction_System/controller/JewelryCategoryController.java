package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.service.JewelryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jewelryCategory")
public class JewelryCategoryController {
    @Autowired
    private JewelryCategoryService jewelryCategoryService;

    @GetMapping("/search/{searchString}")
    public List<JewelryCategoryDTO> searchJewelryCategory(@PathVariable String searchString) {
        List<JewelryCategoryDTO> resultList = jewelryCategoryService.searchJewelryCategory(searchString);
        if (resultList.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        return resultList;
    }
}
