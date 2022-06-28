package com.example.spring.batch.demo.bean;

import lombok.Data;

@Data
public class MongoData {
    private Id id;
    private String entitle;
    private String description;

    @Override
    public String toString() {
        return "MongoData{" +
                "id=" + id +
                ", entitle='" + entitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
