package com.example.spring.batch.demo.paging;

import com.example.spring.batch.demo.bean.DbData;
import com.example.spring.batch.demo.paging.step.BeanProcessor;
import com.example.spring.batch.demo.paging.step.BeansWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class PagingBatchConfigJpa extends DefaultBatchConfigurer {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private DataSource anotherDataSource;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Autowired
    @Qualifier("dataSource")
    public void setAnotherDataSource(DataSource anotherDataSource) {
        this.anotherDataSource = anotherDataSource;
    }

    @Autowired
    public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Autowired
    public void setStepBuilderFactory(
            StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public ItemWriter<DbData> jpaPagingWriter() {
        return new BeansWriter();
    }

    @Bean
    public ItemProcessor<DbData, DbData> jpaPagingProcessor() {
        return new BeanProcessor();
    }


    @Bean
    @JobScope
    public ItemReader<DbData> jpaPagingReader(@Value("#{jobParameters['maxId']?:50L}") Long maxId) {
        Map<String, Object> parameters = Map.of("maxId", maxId.intValue());
        Map<String, Order> sortKeys = Map.of("id", Order.ASCENDING);
        JdbcPagingItemReader<DbData> build1 =
                new JdbcPagingItemReaderBuilder<DbData>().dataSource(anotherDataSource)
                        .name("JdbcPagingItemReader")
                        .pageSize(3)
                        .rowMapper((rs, index) -> DbData.builder()
                                .id(rs.getInt("id"))
                                .customerId(rs.getString("customer_id"))
                                .entitle(rs.getString("entitle"))
                                .description(rs.getString("description"))
                                .userId(rs.getString("user_id"))
                                .build())
                        .selectClause("*")
                        .fromClause("customer_table")
                        .whereClause("id<:maxId")
                        .sortKeys(sortKeys)
                        .parameterValues(parameters)
                        .build();
        return build1;
    }

    @Bean
    public Step jpaPagingStep(ItemReader<DbData> jpaPagingReader) {
        return stepBuilderFactory.get("jpaPagingStep")
                .<DbData, DbData>chunk(3)
                .reader(jpaPagingReader)
                .processor(jpaPagingProcessor())
                .writer(jpaPagingWriter())
                .build();
    }

    @Bean
    public Job jpaPagingJob(Step jpaPagingStep) {
        Job jpaPagingJob = jobBuilderFactory.get("jpaPagingJob")
                .incrementer(new RunIdIncrementer())
                .start(jpaPagingStep)
                .build();
        return jpaPagingJob;
    }


}
