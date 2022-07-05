package com.example.spring.batch.demo.data.source.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.example.spring.batch.demo.dao")
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    @Bean
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public MongoClientFactoryBean mongoClientFactoryBean() {
        MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();
//        mongoClientFactoryBean.setPort(27017);
//        mongoClientFactoryBean.setHost("localhost");
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/liqiming?retryWrites" +
                "=false");
        mongoClientFactoryBean.setConnectionString(connectionString);
        return mongoClientFactoryBean;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClientFactoryBean mongoClientFactoryBean) throws Exception {
        MongoClient mongoClient = mongoClientFactoryBean.getObject();
        return new MongoTemplate(mongoClient, "liqiming");

    }

    @Override
    protected String getDatabaseName() {
        return "liqiming";
    }
}


