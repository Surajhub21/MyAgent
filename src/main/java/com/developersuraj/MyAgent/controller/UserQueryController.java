package com.developersuraj.MyAgent.controller;

import com.developersuraj.MyAgent.services.AgentManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserQueryController {

    @Autowired
    private AgentManager agentManager; // Now calling the new multi-agent orchestrator

    @PostMapping("/ask/{userName}")
    public String getAiResponse(@PathVariable String userName, @RequestBody String query) {
        try {
            return agentManager.processQuery(query, userName);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return "An error occurred while processing your query.";
        }
    }
}

