package com.developersuraj.MyAgent.controller;

import com.developersuraj.MyAgent.services.AgentManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*")
public class UserQueryController {

    @Autowired
    private AgentManager agentManager; // Now calling the new multi-agent orchestrator

    @PostMapping("/ask")
    public String getAiResponse(@RequestBody String query) {
        try {
            return agentManager.processQuery(query);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return "An error occurred while processing your query.";
        }
    }
}

