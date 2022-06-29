package com.example.spring.batch.demo.dao;

import com.example.spring.batch.demo.bean.mysql.CustomerRepo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepoDao extends CrudRepository<CustomerRepo, Integer> {
}
