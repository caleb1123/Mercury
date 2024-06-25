package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.service.JewelryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
