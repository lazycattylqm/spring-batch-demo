package com.example.spring.batch.demo.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // TODO Auto-generated method stub
    log.info("AppRunner run() method started...");
  }

}
