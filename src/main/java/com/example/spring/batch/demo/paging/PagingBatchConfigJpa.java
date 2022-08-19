package com.example.spring.batch.demo.paging;

import com.example.spring.batch.demo.bean.CustomerRepo;
import com.example.spring.batch.demo.paging.step.BeanProcessor;
import com.example.spring.batch.demo.paging.step.BeansWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@EnableBatchProcessing
public class PagingBatchConfigJpa extends DefaultBatchConfigurer {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        return factory.getObject();
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
    public ItemWriter<CustomerRepo> jpaPagingWriter() {
        return new BeansWriter();
    }

    @Bean
    public ItemProcessor<CustomerRepo, CustomerRepo> jpaPagingProcessor() {
        return new BeanProcessor();
    }

    @Bean
    public JpaPagingItemReader<CustomerRepo> readReg() {
        JpaNativeQueryProvider<CustomerRepo> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setEntityClass(CustomerRepo.class);
        queryProvider.setSqlQuery("");
        JpaPagingItemReader<CustomerRepo> build =
                new JpaPagingItemReaderBuilder<CustomerRepo>().pageSize(5)
                        .name("jpaPagingReader")
                        .queryProvider(queryProvider)
                        .entityManagerFactory(entityManagerFactory)
                        .build();

        return build;
    }

    @Bean
    public ItemReader<CustomerRepo> jpaPagingReader(JpaPagingItemReader<CustomerRepo> readReg) {
        JpaNativeQueryProvider<CustomerRepo> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setEntityClass(CustomerRepo.class);
        queryProvider.setSqlQuery("select * from customer_table");
        readReg.setQueryProvider(queryProvider);
        readReg.setParameterValues(new HashMap<String, Object>());
        return readReg;
    }

    @Bean
    public Step jpaPagingStep(ItemReader<CustomerRepo> jpaPagingReader) {
        return stepBuilderFactory.get("jpaPagingStep")
                .<CustomerRepo, CustomerRepo>chunk(3)
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
