package com.dbms.demo.Entities;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
@Getter
@Setter
public class Compensates {
    @EmbeddedId
    private CompensatesId id;

    @Column(name = "PaymentID", unique = true, nullable = false)
    private int paymentID;

    @ManyToOne
    @MapsId("driverID")
    @JoinColumn(name = "DriverID")
    private Driver driver;

    @ManyToOne
    @MapsId("citationNumber")
    @JoinColumn(name = "CitationNumber")
    private Citations citation;
}

@Embeddable
@Getter
@Setter
class CompensatesId implements Serializable {
    @Column(name = "DriverID")
    private String driverID;

    @Column(name = "CitationNumber")
    private String citationNumber;
}