package com.developersuraj.MyAgent.services;


import com.developersuraj.MyAgent.services.externalsource.AiResponseAgent;
import com.developersuraj.MyAgent.services.externalsource.WebSearchAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AgentManager {

    @Autowired
    private MemoryAgent memoryAgent;

    @Autowired
    private WebSearchAgent webSearchAgent;

    @Autowired
    private AiResponseAgent aiResponseAgent;

    public String processQuery(String query) {

        // Check Redis cache first
        String cachedResponse = memoryAgent.getCachedResponse(query);
        if (cachedResponse != null) {

            return cachedResponse;

        }

        // Check if query requires web search
        boolean needsSearch = query.toLowerCase().matches(".*\\b(latest|current|today|recent|news|trending|last update|)\\b.*");
        String processedQuery = needsSearch ? webSearchAgent.search(query) : query;

        // Get AI-generated response
        String response = aiResponseAgent.getResponse(processedQuery);

        // Save response to Redis and MongoDB history
        memoryAgent.cacheResponse(query, response);

        return response;
    }
}