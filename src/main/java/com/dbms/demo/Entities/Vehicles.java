package com.dbms.demo.Entities;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Getter
@Setter
public class Vehicles {
    @Id
    private String licenseNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String driverID;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "driverID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Driver driver;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<IssuedTo> issuedTo;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Permits> permits;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Occupies> occupies;
}
