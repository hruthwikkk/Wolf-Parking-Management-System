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

@Entity
@Getter
@Setter
public class Appeals {
    @EmbeddedId
    private AppealsId id;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "driverID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "citationNumber", referencedColumnName = "citationNumber", insertable = false, updatable = false)
    private Citations citation;
}

@Embeddable
@Getter
@Setter
class AppealsId implements Serializable {
    @Column(name = "DriverID")
    private String driverID;

    @Column(name = "CitationNumber")
    private String citationNumber;
}
