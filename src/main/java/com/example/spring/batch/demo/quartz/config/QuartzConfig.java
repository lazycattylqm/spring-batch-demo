package com.example.spring.batch.demo.quartz.config;

import com.example.spring.batch.demo.quartz.JobBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

//@Configuration
public class QuartzConfig {

    private JobBean jobBean;

    @Autowired
    public void setJobBean(JobBean jobBean) {
        this.jobBean = jobBean;
    }

    @Bean
    public JobDetailFactoryBean jobDetailBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(jobBean.getClass());
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailBean) {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(jobDetailBean.getObject());
        bean.setCronExpression("0/5 * * * * ?");
        return bean;
    }
}
