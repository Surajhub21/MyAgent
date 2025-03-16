package com.developersuraj.MyAgent.services.externalsource;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiResponseAgent {

    private final GoogleAiGeminiChatModel chatModel;

    public AiResponseAgent(@Value("${GEMINI_API_KEY}") String apiKey) {
        this.chatModel = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-1.5-flash")
                .build();
    }

    public String getResponse(String prompt) {
        return chatModel.chat(prompt);
    }
}