package com.dbms.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.demo.Service.AppealService;

@RestController
@RequestMapping(path="/appeal") 
public class AppealController {


    private final AppealService appealService;

    @Autowired
    public AppealController(AppealService appealService) {
        this.appealService = appealService;
    }
    //Handles HTTP POST requests to add appeal data.
    @PostMapping("/add")
    public String addAppeal(String driverID, String citationNumber, String details, boolean status) {
        boolean isValid = appealService.addAppeal(driverID, citationNumber, details, status);
        if(isValid)
            return "Appeal data added successfully!";

        return "Appeal data not added";
    }
    //Handles HTTP POST requests to update appeal data.
    @PostMapping("/update")
    public String updateAppeal(String driverID, String citationNumber, String details, String status) {
        boolean isValid = appealService.updateAppeal(driverID, citationNumber, details, status);
        if(isValid)
            return "Appeal data updated successfully!";

        return "Appeal data not updated successfully!";
    }
    //Handles HTTP DELETE requests to delete appeal data.
    @DeleteMapping("/delete")
    public String deleteAppeal(String driverId, String citationNumber) {
        boolean isValid = appealService.deleteAppealById(driverId, citationNumber);
        if(isValid)
            return "Appeal data deleted successfully!";

        return "Appeal data not deleted successfully!";
    }
}