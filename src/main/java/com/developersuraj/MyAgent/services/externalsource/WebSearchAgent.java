package com.developersuraj.MyAgent.services.externalsource;

import com.developersuraj.MyAgent.model.WebSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WebSearchAgent {

    @Value("${WEB_SEARCH_API_KEY}")
    private String apiKey;

    @Value("${WEB_SEARCH_ID}")
    private String searchEngineId;

    private final WebClient webClient;

    @Autowired
    public WebSearchAgent(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String search(String query) {
        String url = String.format("https://www.googleapis.com/customsearch/v1?q=%s&key=%s&cx=%s",
                URLEncoder.encode(query, StandardCharsets.UTF_8), apiKey, searchEngineId);

        try {
            WebSearchResult response = webClient.get()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(WebSearchResult.class)
                    .block();

            return formatResults(query, response);
        } catch (Exception e) {
            return "No relevant results found.";
        }
    }

    private String formatResults(String query, WebSearchResult searchResults) {
        if (searchResults == null || searchResults.getItems() == null) return "No results found.";

        StringBuilder sb = new StringBuilder();
        sb.append("Fetched web data for: ").append(query).append("\n\n");

        for (WebSearchResult.SearchItem item : searchResults.getItems()) {
            sb.append("- ").append(item.getTitle()).append("\n")
                    .append("  ").append(item.getLink()).append("\n")
                    .append("  ").append(item.getSnippet()).append("\n\n");
        }
        return sb.toString();
    }
}