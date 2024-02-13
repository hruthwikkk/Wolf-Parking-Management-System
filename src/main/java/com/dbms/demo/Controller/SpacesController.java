package com.dbms.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.demo.Service.SpacesService;

@RestController
@RequestMapping(path="/spaces")
public class SpacesController {

    private final SpacesService spacesService;

    @Autowired
    public SpacesController(SpacesService spacesService) {
        this.spacesService = spacesService;
    }
    //Handles HTTP POST requests to add space data.
    @PostMapping("/add")
    public String addSpace(int spaceNumber, String spaceType, String availabilityStatus, String zoneID, int lotID) {
        boolean isValid = spacesService.addSpaces(spaceNumber, spaceType, availabilityStatus, zoneID, lotID);
        if(isValid)
            return "Spaces data added successfully!";
        return "Spaces data not added";
    }

    @PostMapping("/update")
    public String updateSpace(int spaceNumber, String spaceType, String availabilityStatus, String zoneID, int lotID) {
        boolean isValid = spacesService.updateSpaces(spaceNumber, spaceType, availabilityStatus, zoneID, lotID);
        if(isValid)
            return "Space data updated successfully!";
        return "Space data not updated";
    }

    //Handles HTTP DELETE requests to delete space data.
    @DeleteMapping("/delete")
    public String deleteSpace(int spaceNumber, String zoneID, int lotID) {
        boolean isValid = spacesService.deleteSpaceById(spaceNumber, zoneID, lotID);
        if(isValid)
            return "Space data deleted successfully!";
        return "Space data not deleted";
    }
}