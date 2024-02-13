package com.dbms.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Getter
@Setter
public class Zone {
    @EmbeddedId
    private ZoneId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lotID", referencedColumnName = "ID", insertable = false, updatable = false)
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private List<Permits> permits;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private List<Spaces> spaces;
}

@Embeddable
@Getter
@Setter
class ZoneId implements Serializable {
    @Column(name = "ID")
    private String ID;

    @Column(name = "LotID")
    private int lotID;
}
