package com.dbms.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.demo.Service.ZoneService;

@RestController
@RequestMapping(path="/zone")
public class ZoneController {
    private final ZoneService zoneService;

    @Autowired
    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    //Handles HTTP POST requests to add Zone data.
    @PostMapping("/add")
    public String addZone(String id, int lotID) {
        boolean isValid = zoneService.addZones(id, lotID);
        if(isValid)
            return "Zone data added successfully!";
        return "Zone data not added";
    }

    //Handles HTTP DELETE requests to delete zone data.
    @DeleteMapping("/delete")
    public String deleteZone(String id, int lotID) {
        boolean isValid = zoneService.deleteZoneById(id, lotID);
        if(isValid)
            return "Zone data deleted successfully!";
        return "Zone data not deleted";
    }
}