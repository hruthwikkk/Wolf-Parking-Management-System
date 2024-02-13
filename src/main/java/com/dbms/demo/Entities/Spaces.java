package com.dbms.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Getter
@Setter
public class Spaces {
    @EmbeddedId
    private SpacesId id;

    @Column(nullable = false)
    private String spaceType;

    @Column(nullable = false)
    private String availabilityStatus;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ZoneID", referencedColumnName = "ID", insertable = false, updatable = false),
            @JoinColumn(name = "LotID", referencedColumnName = "LotID", insertable = false, updatable = false)
    })
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "lotID", referencedColumnName = "ID", insertable = false, updatable = false)
    private ParkingLot parkingLot;

}

@Embeddable
@Getter
@Setter
class SpacesId implements Serializable {
    @Column(name = "SpaceNumber")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int spaceNumber;

    @Column(name = "ZoneID")
    private String zoneID;

    @Column(name = "LotID")
    private int lotID;
}
