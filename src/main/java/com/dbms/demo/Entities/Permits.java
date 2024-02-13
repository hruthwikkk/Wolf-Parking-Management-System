package com.dbms.demo.Entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
public class Permits {
    @EmbeddedId
    private PermitsId id;

    @Column(nullable = false)
    private String spaceType;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(nullable = false)
    private Time expirationTime;

    @Column(nullable = false)
    private String permitType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driverID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ZoneID", referencedColumnName = "ID", insertable = false, updatable = false),
            @JoinColumn(name = "LotID", referencedColumnName = "LotID", insertable = false, updatable = false)
    })
    private Zone zone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "licenseNumber", referencedColumnName = "licenseNumber", insertable = false, updatable = false)
    private Vehicles vehicle;
}

@Embeddable
@Getter
@Setter
class PermitsId implements Serializable {
    @Column(name = "ID")
    private String id;

    @Column(name = "DriverID")
    private String driverID;

    @Column(name = "ZoneID")
    private String zoneID;

    @Column(name = "LicenseNumber")
    private String licenseNumber;
}



