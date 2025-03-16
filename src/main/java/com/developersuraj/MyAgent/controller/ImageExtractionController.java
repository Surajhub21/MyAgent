package com.developersuraj.MyAgent.controller;

import com.developersuraj.MyAgent.services.externalsource.ImageExtractionAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/img")
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
