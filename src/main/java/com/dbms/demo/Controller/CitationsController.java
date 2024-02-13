package com.dbms.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dbms.demo.Service.CitationsService;

@RestController
@RequestMapping(path="/citations")
public class CitationsController {
    private final CitationsService citationsService;

    @Autowired
    public CitationsController(CitationsService citationsService) {
        this.citationsService = citationsService;
    }
    //Handles HTTP POST requests to add citation data.
    @PostMapping("/add")
    public String addCitation(
            String citationNumber,
            String category,
            java.sql.Timestamp timestampIssued,
            String paymentStatus,
            String driverID,
            String licenseNumber,
            String lotName,
            String model,
            String color
    ) {
        boolean isValid = citationsService.addCitation(
                citationNumber,
                category,
                timestampIssued,
                paymentStatus,
                driverID,
                licenseNumber,
                lotName,
                model,
                color
        );
        if(isValid)
            return "Citation added successfully!";
        return "Citation not added";
    }
    //Handles HTTP DELETE requests to delete Citation data.
    @DeleteMapping("/delete")
    public String deleteCitation(String citationNumber) {
        boolean isValid = citationsService.deleteCitationById(citationNumber);
        if(isValid)
            return "Citation deleted successfully!";
        return "Citation not deleted";
    }
    //Handles HTTP PUT requests to update payment status.
    @PutMapping("/updatePaymentStatus")
    public String updateCitationPaymentStatus(
             String citationNumber,
             String newPaymentStatus) {
        boolean isValid = citationsService.updateCitationPaymentStatus(citationNumber, newPaymentStatus);
        if(isValid)
            return "Citation payment status updated successfully!";
        return "Citation payment status not updated successfully!";
    }
    //Handles HTTP GET requests to get citation Report.
    @GetMapping("/citationsReport")
    public List<Map<String, Object>> generateCitationReport(int parkingLotId, String startDate, String endDate) {
        return citationsService.generateCitationReport(parkingLotId, startDate, endDate);
    }

    //Handles HTTP POST requests to pay citation.
    @PostMapping("/payCitation")
    public String payCitation(String driverNumber, String citationNumber) {
        boolean isValid = citationsService.payCitation(driverNumber, citationNumber);
        if(isValid)
            return "Citation compensated successfully!";
        return "Citation not compensated successfully!";
  }
}
