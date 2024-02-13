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
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Occupies {
    @EmbeddedId
    private OccupiesId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicleLicenseNumber", referencedColumnName = "licenseNumber", insertable = false, updatable = false)
    private Vehicles vehicle;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "zoneID", referencedColumnName = "ID", insertable = false, updatable = false),
            @JoinColumn(name = "lotID", referencedColumnName = "LotID", insertable = false, updatable = false)
    })
    private Zone zone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lotID", referencedColumnName = "ID", insertable = false, updatable = false)
    private ParkingLot parkingLot;
}

@Embeddable
@Getter
@Setter
class OccupiesId implements Serializable {
    @Column(name = "vehicleLicenseNumber")
    private String vehicleLicenseNumber;

    @Column(name = "spaceNumber")
    private int spaceNumber;

    @Column(name = "zoneID")
    private String zoneID;

    @Column(name = "lotID")
    private int lotID;
}
