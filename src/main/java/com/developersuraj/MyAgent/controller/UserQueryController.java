package com.developersuraj.MyAgent.controller;

import com.developersuraj.MyAgent.model.BodyModel;
import com.developersuraj.MyAgent.services.AgentManager;
import com.developersuraj.MyAgent.services.externalsource.ImageExtractionAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*")
public class UserQueryController {

    @Autowired
    private AgentManager agentManager; // Now calling the new multi-agent orchestrator

    @Autowired
    private ImageExtractionAPI textExtractService;

    @PostMapping("/ask")
    public ResponseEntity<String> getAiResponse(@RequestBody BodyModel bodyModel) {

        try {

            if(bodyModel.getImage() != null && bodyModel.getQuery() != null)
            {
                byte[] imageBytes = downloadImage(bodyModel.getImage());
                String text = textExtractService.extractText(imageBytes);

                String mainQuery = text + "\n" + bodyModel.getQuery();
                return new ResponseEntity<>(agentManager.processQuery(mainQuery), HttpStatus.OK);

            }
            else if (bodyModel.getImage() != null) {

                byte[] imageBytes = downloadImage(bodyModel.getImage());
                String text = textExtractService.extractText(imageBytes);

                return new ResponseEntity<>(agentManager.processQuery(text), HttpStatus.OK);

            }
            else if (bodyModel.getQuery() != null) {

                return new ResponseEntity<>(agentManager.processQuery(bodyModel.getQuery()), HttpStatus.OK);

            }
            else {

                return new ResponseEntity<>("Nothing!", HttpStatus.NO_CONTENT);

            }


        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return new ResponseEntity<>("An error occurred while processing your query.", HttpStatus.BAD_REQUEST);
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

