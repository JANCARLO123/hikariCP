package com.jcpv.example.entity;

import javax.persistence.*;

/**
 * Created by JanCarlo on 20/06/2017.
 */
@Entity
@Table(name="CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name ="CUST_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
