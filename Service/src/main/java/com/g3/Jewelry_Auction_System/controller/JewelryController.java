package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import com.g3.Jewelry_Auction_System.payload.request.JewelryPageRequest;
import com.g3.Jewelry_Auction_System.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jewelry")
public class JewelryController {
    @Autowired
    JewelryService jewelryService;

//    @PreAuthorize("hasAnyRole('STAFF', 'USER')")
    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/add")
    public ResponseEntity<JewelryDTO> addJewelry(@RequestBody JewelryDTO jewelryDTO) {
          var newj = jewelryService.addJewelry(jewelryDTO);
        return new ResponseEntity<>(newj , HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
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

    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/{jewelryId}")
    public ResponseEntity<JewelryDTO> updateJewelry(@RequestBody JewelryDTO jewelryDTO, @PathVariable(value = "jewelryId") int id) {
        JewelryDTO jewelryDTO1 = jewelryService.updateJewelry(jewelryDTO, id);
        if (jewelryDTO1 != null) {
            return new ResponseEntity<>(jewelryDTO1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/getAll/{page}")
    public Page<JewelryDTO> getAllJewelries(@PathVariable(value = "page") Integer offset){
        JewelryPageRequest pageRequest = new JewelryPageRequest(2, offset);
        return jewelryService.getAllJewelry(offset);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/getAll")
    public ResponseEntity<List<JewelryDTO>> getAllJewelries(){
        List<JewelryDTO> list = jewelryService.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3001")

    @GetMapping("/search/{name}")
    public ResponseEntity<List<JewelryDTO>> searchJewelriesByName(@PathVariable String name){
        List<JewelryDTO> result = jewelryService.searchName(name);
        if (result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{id}")
    public ResponseEntity<JewelryDTO> getJewelryDetail(@PathVariable int id){
        JewelryDTO jewelryDetail = jewelryService.getJewelryDetail(id);
        return new ResponseEntity<>(jewelryDetail, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{jewelryId}/auction")
    public ResponseEntity<AuctionDTO> getAuctionByJewelry(@PathVariable int jewelryId) {
        AuctionDTO auction = jewelryService.getAuctionByJewelry(jewelryId);
        if(auction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(auction, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/available")
    public ResponseEntity<List<JewelryDTO>> getJewelryForAuction() {
        List<JewelryDTO> jewelries = jewelryService.getJewelryForAuction();
        return new ResponseEntity<>(jewelries, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/category/{id}")
    public ResponseEntity<List<JewelryDTO>> getJewelryByCategory(@PathVariable int id) {
        List<JewelryDTO> jewelries = jewelryService.getJewelryByCategory(id);
        return new ResponseEntity<>(jewelries, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{id}/jewelry")
    public ResponseEntity<JewelryDTO> getJewelryByAuctionId(@PathVariable("id") int id) {
        try {
            JewelryDTO dto = jewelryService.getJewelryByAuctionId(id);
            if (dto != null) {
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/onAuction")
    public ResponseEntity<List<JewelryDTO>> getJewelryOnAuction() {
        List<JewelryDTO> list = jewelryService.getJewelryOnAuction();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
