package com.example.spring.batch.demo.config;

import com.example.spring.batch.demo.job.Processor;
import com.example.spring.batch.demo.job.Reader;
import com.example.spring.batch.demo.job.Writer;
import com.example.spring.batch.demo.model.Model;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BatchConfig extends DefaultBatchConfigurer {

    @Bean
    public Step step(Reader reader, Processor processor, Writer writer, StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("simpleStep")
                .<Model, Model>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(Step step, JobBuilderFactory jobBuilderFactory) throws Exception {
        return jobBuilderFactory.get("simpleJob").incrementer(new RunIdIncrementer()).start(step).build();
    }


}
