package com.project.SQLpro.controller;

import com.cohere.api.types.ChatResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.project.SQLpro.Repo.SQLRepo;
import com.project.SQLpro.service.SQLService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
//



@Controller
@RequestMapping("/")
public class SQLController {
    private final SQLService sqlService;
    private List<Map<String, Object>> data;
    public SQLController(SQLService sqlService) {
        this.sqlService = sqlService;
        this.data = List.of();
    }

    @GetMapping("/")
    public String execute() {
        return "index";
    }
    
    

    @PostMapping("/register")
    public String show(@RequestParam("query") String input, Model model) throws JsonMappingException, JsonProcessingException {

        this.data = sqlService.getQuery(input);
        model.addAttribute("data", data);
    	return "result";
    }



 //     no longer use
//    @GetMapping("/records")
//    @ResponseBody
//    public  List<Map<String, Object>> executeQuery() throws IOException {
//        return result;
//    }

    
}
