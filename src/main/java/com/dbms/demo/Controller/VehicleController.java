package com.dbms.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.demo.Service.VehicleService;

@RestController
@RequestMapping(path="/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    //Handles HTTP POST requests to add vehicle data.
    @PostMapping("/add")
    public String addVehicle(String licenseNumber, String model, String color, String manufacturer, int year, String driverID) {
        boolean isValid = vehicleService.addVehicle(licenseNumber, model, color, manufacturer, year, driverID);
        if(isValid)
            return "Vehicle data added successfully!";
        return "Vehicle data not added";
    }

    //Handles HTTP DELETE requests to delete vehicle data.
    @DeleteMapping("/delete")
    public String deleteVehicle(String licenseNumber) {
        boolean isValid = vehicleService.deleteVehicleById(licenseNumber);
        if(isValid)
            return "Vehicle data deleted successfully!";
        return "Vehicle data not deleted";
    }
}