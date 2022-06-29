package com.example.spring.batch.demo.data.source.config;

import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.example.spring.batch.demo.dao")
public class MongoDbConfig {

    @Bean
    public MongoClientFactoryBean mongoClientFactoryBean() {
        MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();
        mongoClientFactoryBean.setPort(27017);
        mongoClientFactoryBean.setHost("localhost");
        return mongoClientFactoryBean;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClientFactoryBean mongoClientFactoryBean) throws Exception {
        MongoClient mongoClient = mongoClientFactoryBean.getObject();
        return new MongoTemplate(mongoClient, "liqiming");

    }
}


