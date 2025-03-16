package com.developersuraj.MyAgent.controller;

import com.developersuraj.MyAgent.services.externalsource.ImageExtractionAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/img")
@CrossOrigin(origins = "*")
public class ImageExtractionController {

    @Autowired
    private ImageExtractionAPI textExtractService;

    @PostMapping("/ex")
    public ResponseEntity<?> extractQuestionsFromImage(@RequestParam("image") MultipartFile imageFile) {
        try {
            String questions = textExtractService.extractText(imageFile);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing image: " + e.getMessage());
        }
    }

}
