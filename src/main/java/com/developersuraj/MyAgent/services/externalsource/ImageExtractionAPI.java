package com.developersuraj.MyAgent.services.externalsource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
public class ImageExtractionAPI {

    @Value("${OCR_USERNAME}")
    private String USERNAME ;
    @Value("${OCR_LICENCE}")
    private String LICENCE_CODE;
    @Value("${OCR_URL}")
    private String OCR_MAIN_URL; //Extract English Text

    private final WebClient webClient;

    public ImageExtractionAPI(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String extractText(MultipartFile file) throws IOException {

        if(!file.isEmpty()) {
            byte[] imageBytes = file.getBytes();
            String authHeader = "Basic " + Base64.getEncoder().encodeToString((USERNAME + ":" + LICENCE_CODE).getBytes());

            String response = webClient.post()
                    .uri(OCR_MAIN_URL)
                    .header("Authorization", authHeader)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .bodyValue(imageBytes)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(WebClientResponseException.class, ex -> Mono.just("Error: " + ex.getResponseBodyAsString()))
                    .block();

            return parseOCRText(response);
        }
        else{
            return "NO FIle!";
        }
    }

    private String parseOCRText(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode ocrTextNode = rootNode.path("OCRText");

            // Extract first OCR text if available
            if (ocrTextNode.isArray() && ocrTextNode.size() > 0 && ocrTextNode.get(0).isArray()) {
                return ocrTextNode.get(0).get(0).asText();
            }
        } catch (Exception e) {
            log.error("Error for this data " + jsonResponse + "\n"+ e);
            return "Failed to parse OCR text";
        }
        return "No text found";
    }
}
