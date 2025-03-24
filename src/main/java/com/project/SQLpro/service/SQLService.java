package com.project.SQLpro.service;

import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.ChatMessageV2;
import com.cohere.api.types.ChatResponse;
import com.cohere.api.types.UserMessage;
import com.cohere.api.types.UserMessageContent;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class SQLService {
    private final Cohere cohere;

    public SQLService() {
        this.cohere = Cohere.builder()
                .token("7f4AVAMiaNYbCxMk7PfRopu1GC3DjOEHAyWkKBY6")
                .clientName("SpringBoot")
                .build();
    }

    public ChatResponse getQuery(String query) {
        ChatMessageV2 userMessage = ChatMessageV2.user(
                UserMessage.builder()
                        .content(UserMessageContent.of("Generate an SQL query based on this request: table name `sql_data` '" + query + "'. " +
                                "The database table is `SQLTABLE` with columns: `id`, `name`, and `tech`. Pls give easy query" +
                                "Return only the SQL query as plain text, without explanations, formatting, or markdown.dont use \n and all just give query like query"))
                        .build()
        );

        V2ChatRequest chatRequest = V2ChatRequest.builder()
                .model("command-r-plus")
                .messages(List.of(userMessage))
                .build();
        ChatResponse resp = cohere.v2().chat(chatRequest);
        return resp;
    }
}
