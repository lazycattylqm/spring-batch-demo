package com.example.spring.batch.demo.process;

import com.example.spring.batch.demo.bean.DbData;
import com.example.spring.batch.demo.bean.MongoData;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.stream.Stream;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Autowired
    public void setStepBuilderFactory(
            StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<DbData> reader() {
        Iterator<DbData> iterator =
                Stream.generate(() -> new DbData("cnhsbcliiqming", "userId", "entitle", "description"))
                        .limit(10)
                        .iterator();
        IteratorItemReader<DbData> reader = new IteratorItemReader<>(iterator);
        return reader;
    }

    @Bean
    public DataTransform process() {
        return new DataTransform();
    }

    @Bean
    public ListItemWriter<MongoData> writer(MongoData data) {
//        System.out.println(mongoData);
        return new ListItemWriter<MongoData>();
    }
}
