package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.service.RequestService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;
    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/create")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO dto) {
        RequestDTO createdRequest = requestService.createRequest(dto);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/preliminary/{requestId}")
    public ResponseEntity<Request> updatePreliminaryPrice(@PathVariable int requestId, @RequestBody RequestDTO dto) {
        requestService.updatePreliminaryPrice(requestId, dto);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/final/{requestId}")
    public ResponseEntity<Request> updateFinalPrice(@PathVariable int requestId, @RequestBody RequestDTO dto) {
        requestService.updateFinalPrice(requestId, dto);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list")
    public ResponseEntity<List<RequestDTO>> getRequestList() {
        List<RequestDTO> requestList = requestService.getRequestList();
        if (requestList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/delete/{requestId}")
    public ResponseEntity<Request> deactivateAccount(@PathVariable int requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.ok().build(); // Return 200 OK on successful deactivation
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/{status}")
    public ResponseEntity<List<RequestDTO>> getRequestByStatus(@PathVariable String status) {
        List<RequestDTO> requestList = requestService.getRequestByStatus(status);
        if (requestList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/status/{requestId}")
    public ResponseEntity<Request> updateRequestStatus(@PathVariable int requestId, @RequestBody RequestDTO dto) {
        requestService.updateRequestStatus(requestId, dto);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/thisuser")
    public ResponseEntity<List<RequestDTO>> getRequestByToken() {
        List<RequestDTO> requestList = requestService.getRequestByToken();
        return new ResponseEntity<>(requestList, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3001")

    @PostMapping("/sendDeadlineRequest")
    public ResponseEntity<String> sendDeadlineRequestEmail(@RequestBody RequestDTO requestDTO) {
        try {
            requestService.sendEmailDeadlineRequest(requestDTO);
            return ResponseEntity.ok("Email sent successfully!");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")

    @GetMapping("/id/{id}")
    public ResponseEntity<RequestDTO> getRequestById(@PathVariable("id") int id) {
        RequestDTO requestDTO = requestService.getRequestById(id);
        return ResponseEntity.ok(requestDTO);
    }
}
