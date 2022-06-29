package com.example.spring.batch.demo.newjob.config;

import com.example.spring.batch.demo.bean.CustomerMongo;
import com.example.spring.batch.demo.bean.CustomerRepo;
import com.example.spring.batch.demo.newjob.process.DbProcessor;
import com.example.spring.batch.demo.process.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class DbJobConfig {
    private StepBuilderFactory stepBuilderFactory;

    private JobBuilderFactory jobBuilderFactory;

    private MongoTemplate template;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setTemplate(MongoTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setStepBuilderFactory(
            StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Autowired
    public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job dbLqmJob(JobCompletionNotificationListener listener, Step dbMySqlToMongoETLStep) {
        return jobBuilderFactory.get("dbLqmJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(dbMySqlToMongoETLStep)
                .build();
    }

    @Bean
    public Step dbMySqlToMongoETLStep(ItemReader<CustomerRepo> itemLqmReader,
                                      ItemProcessor<CustomerRepo, CustomerMongo> itemLqmProcessor,
                                      ItemWriter<CustomerMongo> itemLqmWriter) {
        return stepBuilderFactory.get("dbMySqlToMongoETLStep")
                .<CustomerRepo, CustomerMongo>chunk(10)
                .reader(itemLqmReader)
                .processor(itemLqmProcessor)
                .writer(itemLqmWriter)
                .build();
    }

    @Bean
    public ItemReader<CustomerRepo> itemLqmReader() {
        JdbcCursorItemReader<CustomerRepo> itemReader = new JdbcCursorItemReader<>();
        DataSource dataSource = jdbcTemplate.getDataSource();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("SELECT * FROM customer_table");
        itemReader.setRowMapper((row, index) -> CustomerRepo.builder()
                .customerId(row.getString("customer_id"))
                .userId(row.getString("user_id"))
                .entitle(row.getString(
                        "entitle"))
                .description(row.getString("description"))
                .build());

        return itemReader;
    }

    @Bean
    public ItemProcessor<CustomerRepo, CustomerMongo> itemLqmProcessor() {
        return new DbProcessor();
    }

    @Bean
    public ItemWriter<CustomerMongo> itemLqmWriter() {
        MongoItemWriter<CustomerMongo> customerMongoMongoItemWriter = new MongoItemWriter<>();
        customerMongoMongoItemWriter.setTemplate(template);
        return customerMongoMongoItemWriter;
    }
}
