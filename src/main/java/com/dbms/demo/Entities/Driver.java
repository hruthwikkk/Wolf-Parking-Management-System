package com.dbms.demo.Entities;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Driver {
    @Id
    private String ID;
    private String Name;
    private String Status;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Vehicles> vehicles;

    @OneToMany(mappedBy = "driver")
    private List<Citations> citations;

    @OneToMany(mappedBy = "driver")
    private List<Permits> permits;

    @OneToMany(mappedBy = "driver")
    private List<Compensates> compensates;

    @OneToMany(mappedBy = "driver")
    private List<Appeals> appeals;
}
