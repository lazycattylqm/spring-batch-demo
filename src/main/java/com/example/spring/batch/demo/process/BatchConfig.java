package com.example.spring.batch.demo.process;

import com.example.spring.batch.demo.bean.DbData;
import com.example.spring.batch.demo.bean.MongoData;
import com.example.spring.batch.demo.process.item.ContextItemReader;
import com.example.spring.batch.demo.process.item.ContextItemWriter;
import com.example.spring.batch.demo.process.item.ContextProcessor;
import com.example.spring.batch.demo.process.listener.JobCompletionNotificationListener;
import com.example.spring.batch.demo.process.listener.MyItemWriteListener;
import com.example.spring.batch.demo.process.listener.MyReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.stream.Stream;

//@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    private StepExecutionListener stepExecutionListener;

    private ContextItemReader contextItemReader;

    private ContextProcessor contextProcessor;

    private ContextItemWriter contextItemWriter;

    @Autowired
    public void setContextItemReader(ContextItemReader contextItemReader) {
        this.contextItemReader = contextItemReader;
    }

    @Autowired
    public void setContextProcessor(ContextProcessor contextProcessor) {
        this.contextProcessor = contextProcessor;
    }

    @Autowired
    public void setContextItemWriter(ContextItemWriter contextItemWriter) {
        this.contextItemWriter = contextItemWriter;
    }

    @Autowired
    public void setStepExecutionListener(StepExecutionListener stepExecutionListener) {
        this.stepExecutionListener = stepExecutionListener;
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
    public DataTransform processor() {
        return new DataTransform();
    }

    @Bean
    public ListItemWriter<MongoData> writer() {
//        System.out.println(mongoData);
        return new ListItemWriter<MongoData>();
    }

    @Bean
    public Job lqmJob(JobCompletionNotificationListener listener, Step step1, Step step4) {
        return jobBuilderFactory.get("lqmJob")
                .listener(listener)
                .incrementer(new RunIdIncrementer())
                .start(step4)
//                .start(step1)
//                .next(step3())
//                .next(step1)
                .build();
    }

    @Bean
    public Step step1(ListItemWriter<MongoData> writer, MyItemWriteListener writeListener,
                      MyReadListener readListener) {
        return stepBuilderFactory.get("step1")
                .listener(stepExecutionListener)
                .<DbData, MongoData>chunk(10)
                .listener(readListener)
                .listener(writeListener)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .listener(stepExecutionListener)
                .tasklet((contribution, chunkContext) -> {
                    chunkContext.getStepContext()
                            .getStepExecution()
                            .getJobExecution()
                            .getExecutionContext()
                            .put("step2", "step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .listener(stepExecutionListener)
                .tasklet((contribution, chunkContext) -> {
                    Object orDefault = chunkContext.getStepContext()
                            .getJobExecutionContext()
                            .getOrDefault("step2", "key value");
                    System.out.println(orDefault);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .<String, String>chunk(1)
                .reader(contextItemReader)
                .processor(contextProcessor)
                .writer(contextItemWriter)
                .build();
    }


}
