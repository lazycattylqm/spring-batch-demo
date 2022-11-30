package com.example.spring.batch.demo.data.source.config.step;

import com.example.spring.batch.demo.data.source.config.step.reader.MyReader;
import com.example.spring.batch.demo.data.source.config.step.writer.MyWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class MyStep {

    private StepBuilderFactory stepBuilderFactory;

    public MyStep(@Autowired StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<Integer> reader() {
        return new MyReader();
    }

    @Bean
    public ItemWriter<Integer> writer() {
        return new MyWriter();
    }

    @Bean
    public Step liqimingStep(ItemReader<Integer> reader, ItemWriter<Integer> writer) {
        return stepBuilderFactory.get("liqimingStep")
                .<Integer, Integer>chunk(2)
                .reader(reader)
                .writer(writer)
                .build();
    }


}
