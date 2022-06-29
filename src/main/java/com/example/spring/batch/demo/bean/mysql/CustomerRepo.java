package com.example.spring.batch.demo.bean.mysql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("customer_table")
public class CustomerRepo {

    @Id
    private int id;

    @Column("customer_id")
    private String customerId;

    @Column(value = "user_id")
    private String userId;

    @Column(value = "entitle")
    private String entitle;

    @Column(value = "description")
    private String description;
}
