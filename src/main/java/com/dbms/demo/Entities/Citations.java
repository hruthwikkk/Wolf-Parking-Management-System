package com.dbms.demo.Entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
@Getter
@Setter
public class Citations {
    @Id
    private String citationNumber;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private java.sql.Timestamp timestampIssued;

    @Column(nullable = false)
    private String paymentStatus;

    @Column(nullable = false)
    private String fee;

    @Column(nullable = false)
    private String driverID;

    @Column(nullable = false)
    private String licenseNumber;

    private String lotName;

    private String model;

    private String color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driverID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Driver driver;

    @OneToOne(mappedBy = "citation", cascade = CascadeType.ALL)
    private Compensates compensates;
}
