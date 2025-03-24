package com.project.SQLpro.controller;

import com.cohere.api.types.ChatResponse;
import com.project.SQLpro.Repo.SQLRepo;
import com.project.SQLpro.model.Sql;
import com.project.SQLpro.service.SQLService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class SQLController {
    private final SQLService sqlService;
    private final SQLRepo sqlRepo;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON Parser

    public SQLController(SQLService sqlService, SQLRepo sqlRepo) {
        this.sqlService = sqlService;
        this.sqlRepo = sqlRepo;
    }

    @GetMapping("/")
    public String execute() {
        return "index";
    }
    
    @GetMapping("/test")
    public String test() {
        return "index";
    }
    
//    @PostMapping("/register")
//    @ResponseBody
//    public ResponseEntity<?> executeQuery(@RequestParam("query") String input) throws IOException {
//        System.out.println("User Input: " + input);
//
//        // Get AI-generated response as a String (JSON format)
//        ChatResponse response = sqlService.getQuery(input);
//        String jsonResponse = response.toString();
//        System.out.println("Raw AI Response: " + jsonResponse);
//
//        // Parse JSON response
//        JsonNode rootNode = objectMapper.readTree(jsonResponse);
//        JsonNode messageNode = rootNode.path("message").path("content");
//
//        // Extract SQL query text
//        String sqlQuery = "";
//        if (messageNode.isArray() && messageNode.size() > 0) {
//            sqlQuery = messageNode.get(0).path("text").asText(); // Extract query
//        }
//
//        System.out.println("Extracted SQL Query: " + sqlQuery);
//
//        // Ensure the SQL query is valid before execution
//        if (sqlQuery.isEmpty()) {
//            return ResponseEntity.badRequest().body("Invalid SQL query generated.");
//        }
//
//        // Execute SQL query (You need a custom method for dynamic queries)
//        List<Sql> result = sqlRepo.executeCustomQuery(sqlQuery); // Fix this method in your repository
//
//        System.out.println("Query Result: " + result);
//        return ResponseEntity.ok(result);
//    }
    @PostMapping("/register")
    public String executeQuery(@RequestParam("query") String input, Model model){
        System.out.println("User Input: " + input);

        // Get AI-generated response as a String (JSON format)
        ChatResponse response = sqlService.getQuery(input);
        
        System.out.print("Prerna Aparna"+response);
        
        String jsonResponse = response.toString();
        System.out.println("Raw AI Response: " + jsonResponse);

        // Parse JSON response
        //JsonNode rootNode = objectMapper.readTree(jsonResponse);
        

        String sqlQuery = "";
        try {
        JsonNode messageNode = objectMapper.readTree(jsonResponse).path("message").path("content");
        System.out.print(messageNode);
        
        // Extract SQL query text
        if (messageNode.isArray() && messageNode.size() > 0) {
            sqlQuery = messageNode.get(0).path("text").asText(); // Extract query
        }

        System.out.println("Extracted SQL Query: " + sqlQuery);

        }catch(IOException e) {
        	
        }

        // Ensure the SQL query is valid before execution
        if (sqlQuery.isEmpty()) {
            model.addAttribute("error", "Invalid SQL query generated.");
            return "error"; // Redirect to an error page
        }

        // Execute SQL query
        List<Sql> result = sqlRepo.executeCustomQuery(sqlQuery); // Ensure this method exists in SQLRepo

        System.out.println("Query Result: " + result);

        // Pass the result to the HTML page
        model.addAttribute("results", result);
        return "result"; // This will return result.html
    }

    
}
