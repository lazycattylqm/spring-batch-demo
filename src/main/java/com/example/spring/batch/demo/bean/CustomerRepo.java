package com.example.spring.batch.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer_table")
public class CustomerRepo {
    @Id
    private int id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "entitle")
    private String entitle;

    @Column(name = "description")
    private String description;
}
