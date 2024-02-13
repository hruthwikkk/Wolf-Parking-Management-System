package com.dbms.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.demo.Service.PermitsService;

import java.sql.Date;
import java.sql.Time;

@RestController
@RequestMapping(path="/permits")
public class PermitsController {

    private final PermitsService permitsService;

    @Autowired
    public PermitsController(PermitsService permitsService) {
        this.permitsService = permitsService;
    }

    //Handles HTTP POST requests to add permit data.
    @PostMapping("/add")
    public String addPermit(String ID, String spaceType, Date startDate, Date expirationDate, Time expirationTime, String permitType, String driverID, String zoneID, String licenseNumber) {
        boolean isValid = permitsService.addPermit(ID, spaceType, startDate, expirationDate, expirationTime, permitType, driverID, zoneID, licenseNumber);
        if(isValid)
            return "Permit data added successfully!";
        return "Permit data not added";
    }

    @PostMapping("/update")
    public String updatePermit(String ID, String spaceType, Date startDate, Date expirationDate, Time expirationTime, String permitType) {
        boolean isValid = permitsService.updatePermit(ID, spaceType, startDate, expirationDate, expirationTime, permitType);
        if(isValid)
            return "Permit data updated successfully!";
        return "Permit data not updated";
    }

    //Handles HTTP DELETE requests to delete permit data.
    @DeleteMapping("/delete")
    public String deletePermit(String id) {
        boolean isValid = permitsService.deletePermitById(id);
        if(isValid)
            return "Permit data deleted successfully!";
        return "Permit data not deleted";
    }

    @GetMapping("/checkValidity")
    public String isValidPermit(String licenseNumber) {
        int isValid = permitsService.checkValidPermit(licenseNumber);
        if(isValid>0)
            return "Permit is valid for the vehicle "+licenseNumber;

        return "Permit is not valid for the vehicle "+licenseNumber;
    }
}