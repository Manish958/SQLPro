package com.project.SQLpro;


import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;
import java.util.List;
public class Test {
    public static void main(String[] args) {
        Cohere cohere = Cohere.builder().token("7f4AVAMiaNYbCxMk7PfRopu1GC3DjOEHAyWkKBY6").clientName("SpringBoot").build();
        ChatResponse response =
                cohere.v2()
                        .chat(V2ChatRequest.builder()
.model("command-r-plus").messages(List.of(ChatMessageV2.user(UserMessage.builder()
.content(UserMessageContent.of("I have a tablr name City with column city id and city name i want to find city name is jabalpur please give me sql Quarry and try to give only query , remove ``` also and name text as query"))
         .build()))).build());
        System.out.println(response);
    }
}
