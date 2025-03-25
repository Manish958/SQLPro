package com.project.SQLpro.service;

import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.ChatMessageV2;
import com.cohere.api.types.ChatResponse;
import com.cohere.api.types.UserMessage;
import com.cohere.api.types.UserMessageContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SQLpro.Repo.SQLRepo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SQLService {
    private final Cohere cohere;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON Parser
    private final String coherekey= "7f4AVAMiaNYbCxMk7PfRopu1GC3DjOEHAyWkKBY6";
    private final String promp1="I have table name Employee amd Employee_Account The database table is `Employee` with columns: `Employee_ID`, `Employee_Name`, `HireDate` , `JobTitle`,`Department`  and  second table name 'Employee_Account' with columns 'EmployeeID' 'Salary' ,'Bank','AccountNo' EmployeeID in both tables are FOREIGN KEY." ;
    private final String promp2= " Pls give SQL query Return only the SQL query as plain text, without explanations, formatting, or markdown.dont use \\n and sql and ` and all just give query like query in single line and also show EmployeeID Strictely follow column name";
    @Autowired
    SQLRepo sqlRepo;

    public SQLService() {
        this.cohere = Cohere.builder()
                .token(coherekey)
                .clientName("SpringBoot")
                .build();
    }


    public List<Map<String, Object>> getQuery(String query) throws JsonMappingException, JsonProcessingException {
        ChatMessageV2 userMessage = ChatMessageV2.user(
                UserMessage.builder()
                        .content(UserMessageContent.of(promp1 + query + promp2))
                        .build()
        );

        
        V2ChatRequest chatRequest = V2ChatRequest.builder()
                .model("command-r-plus")
                .messages(List.of(userMessage))
                .build();
        ChatResponse response = cohere.v2().chat(chatRequest);
        
        String jsonResponse = response.toString();
        // Parse JSON response
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode messageNode = rootNode.path("message").path("content");
        String sqlQuery = "";
        if (messageNode.isArray() && !messageNode.isEmpty()) {
            sqlQuery = messageNode.get(0).path("text").asText(); // Extract query
        }
        if(sqlQuery.contains("Error")){
            System.out.println("please enter correct value");
        }
        System.out.println(sqlQuery);
        return sqlRepo.executeCustomQuery(sqlQuery);
    }
}
