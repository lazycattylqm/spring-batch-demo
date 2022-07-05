package com.example.spring.batch.demo.controller;

import com.example.spring.batch.demo.bean.mongo.CustomerMongo;
import com.example.spring.batch.demo.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {


    private QueryService queryService;

    @Autowired
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }



    @GetMapping("/query")
    public String query() {
        queryService.query();
        return "query hello";
    }

    @GetMapping("/queryMongo")
    public List<CustomerMongo> queryMongo() {
        return queryService.queryMongo();
    }

    @GetMapping("/insertTwoToMongo")
    public String insertTwoToMongo() {
        queryService.insertTwoToMongo();
        return "insertTwoToMongo hello";
    }
}
