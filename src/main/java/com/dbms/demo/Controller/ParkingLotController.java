package com.dbms.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.demo.Service.ParkingLotService;

@RestController
@RequestMapping(path="/lot")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @Autowired
    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }
    //Handles HTTP POST requests to add lot data.
    @PostMapping("/add")
    public String addLot(int ID, String name, String address) {
        boolean isValid = parkingLotService.addLot(ID, name, address);
        if(isValid)
            return "ParkingLot data added successfully!";
        return "ParkingLot data not added";
    }

    @PostMapping("/update")
    public String updateLot(int id, String name, String address) {
        boolean isValid = parkingLotService.updateLot(id, name, address);
        if(isValid)
            return "ParkingLot data updated successfully!";
        return "ParkingLot data not updated";
    }

    //Handles HTTP DELETE requests to delete parking lot data.
    @DeleteMapping("/delete")
    public String deleteLot(int id) {
        boolean isValid = parkingLotService.deleteLotById(id);
        if(isValid)
            return "ParkingLot data deleted successfully!";
        return "ParkingLot data was not deleted successfully!";
    }
}