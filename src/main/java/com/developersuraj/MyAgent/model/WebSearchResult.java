package com.developersuraj.MyAgent.model;

import lombok.Data;

import java.util.List;

@Data
public class WebSearchResult {
    private List<SearchItem> items;

    @Data
    public static class SearchItem {
        private String title;
        private String link;
        private String snippet;
    }
}


