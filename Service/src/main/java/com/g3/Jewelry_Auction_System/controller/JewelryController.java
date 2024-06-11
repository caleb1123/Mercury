package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import com.g3.Jewelry_Auction_System.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jewelry")
public class JewelryController {
    @Autowired
    JewelryService jewelryService;

    @PostMapping("/add")
    public ResponseEntity<JewelryDTO> addJewelry(@RequestBody JewelryDTO jewelryDTO) {
        JewelryDTO newJewelry = jewelryService.addJewelry(jewelryDTO);
        return new ResponseEntity<>(newJewelry, HttpStatus.CREATED);
    }

    @PutMapping("/delist/{jewelryId}")
    public ResponseEntity<Jewelry> delistJewelry(@PathVariable int jewelryId) {
        try {
            jewelryService.delistJewelry(jewelryId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle other potential exceptions (e.g., database issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 Internal Server Error
        }

    }

    @PutMapping("/update/{jewelryId}")
    public ResponseEntity<JewelryDTO> updateJewelry(@RequestBody JewelryDTO jewelryDTO, @PathVariable(value = "jewelryId") int id) {
        try {
            jewelryService.updateJewelry(jewelryDTO, id);
            return ResponseEntity.ok().build();
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getAll")
    public List<JewelryDTO> getAllJewelries(){
        List<JewelryDTO> jewelryDTOList = jewelryService.getAllJewelry();
        if (jewelryDTOList.isEmpty()){
            ResponseEntity.notFound().build();
        }
        return jewelryDTOList;
    }

}
