package com.example.spring.batch.demo.newjob.config;

import com.example.spring.batch.demo.bean.CustomerMongo;
import com.example.spring.batch.demo.bean.CustomerRepo;
import com.example.spring.batch.demo.newjob.process.DbProcessor;
import com.example.spring.batch.demo.process.listener.JobCompletionNotificationListener;
import com.example.spring.batch.demo.repo.CustomerRepoDao;
import com.example.spring.batch.demo.repo.MongoCustomerRepoDao;
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
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class DbJobConfig {
    private StepBuilderFactory stepBuilderFactory;

    private JobBuilderFactory jobBuilderFactory;

    private MongoTemplate template;

    private JdbcTemplate jdbcTemplate;

    private CustomerRepoDao customerRepo;


    private MongoCustomerRepoDao mongoCustomerRepo;

    @Autowired
    public void setMongoCustomerRepo(MongoCustomerRepoDao mongoCustomerRepo) {
        this.mongoCustomerRepo = mongoCustomerRepo;
    }

    @Autowired
    public void setCustomerRepo(CustomerRepoDao customerRepo) {
        this.customerRepo = customerRepo;
    }


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
        RepositoryItemReader<CustomerRepo> itemReader = new RepositoryItemReader<>();
        itemReader.setRepository(customerRepo);
        itemReader.setMethodName("findAll");
        Map<String, Sort.Direction> sorts = Map.of("id", Sort.Direction.ASC);
        itemReader.setSort(sorts);
        return itemReader;
    }

    @Bean
    public ItemProcessor<CustomerRepo, CustomerMongo> itemLqmProcessor() {
        return new DbProcessor();
    }

    @Bean
    public ItemWriter<CustomerMongo> itemLqmWriter() {
        RepositoryItemWriter<CustomerMongo> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(mongoCustomerRepo);
        itemWriter.setMethodName("save");
        return itemWriter;
    }
}
