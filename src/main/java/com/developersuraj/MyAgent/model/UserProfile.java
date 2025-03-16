package com.developersuraj.MyAgent.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfile {

    @Id
    private ObjectId id;

    private String userName;
    @Indexed(unique = true , background = true)
    private String email;
    private String role;

    private String preferredLanguage;
    private List<ChatHistory> queryHistory= new ArrayList<>();;

    @Data
    @NoArgsConstructor
    public static class ChatHistory implements Serializable {
        private String query;
        private String response;

        public ChatHistory(String query , String response){
            this.query = query;
            this.response = response;
        }
    }
}
