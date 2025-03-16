package com.developersuraj.MyAgent.controller;

import com.developersuraj.MyAgent.services.externalsource.ImageExtractionAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
@RequestMapping("/img")
@CrossOrigin(origins = "*")
public class ImageExtractionController {

    @Autowired
    private ImageExtractionAPI textExtractService;

    @PostMapping("/ex")
    public ResponseEntity<?> extractQuestionsFromImage(@RequestParam("image") String imageUrl) {
        try {
            // Download image bytes
            byte[] imageBytes = downloadImage(imageUrl);

            String text = textExtractService.extractText(imageBytes);

            return new ResponseEntity<>(text, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] downloadImage(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }

}
