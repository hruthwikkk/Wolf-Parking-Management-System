package com.dbms.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class IssuedTo {
    @EmbeddedId
    private IssuedToId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "licenseNumber", referencedColumnName = "licenseNumber", insertable = false, updatable = false)
    private Vehicles vehicle;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "citationNumber", referencedColumnName = "citationNumber", insertable = false, updatable = false)
    private Citations citation;
}

@Embeddable
@Getter
@Setter
class IssuedToId implements Serializable {
    @Column(name = "LicenseNumber")
    private String licenseNumber;

    @Column(name = "CitationNumber")
    private String citationNumber;
}
