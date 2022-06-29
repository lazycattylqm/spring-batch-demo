package com.example.spring.batch.demo.quartz;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JobBean extends QuartzJobBean {

    private Job dbLqmJob;

    private JobLauncher jobLauncher;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setDbLqmJob(Job dbLqmJob) {
        this.dbLqmJob = dbLqmJob;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("JobBean executeInternal Hello Quartz");
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        JobParameters parameters = jobParametersBuilder.addDate("date", Date.from(Instant.now()))
                .toJobParameters();
        try {
            jobLauncher.run(dbLqmJob, parameters);
        } catch (JobExecutionAlreadyRunningException e) {
            log.error("JobExecutionAlreadyRunningException", e);
        } catch (JobRestartException e) {
            log.error("JobRestartException", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            log.error("JobInstanceAlreadyCompleteException", e);
        } catch (JobParametersInvalidException e) {
            log.error("JobParametersInvalidException", e);
        }

    }
}
