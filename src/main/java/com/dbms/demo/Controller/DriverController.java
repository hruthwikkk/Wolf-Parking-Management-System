package com.dbms.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.demo.Service.DriverService;

@RestController
@RequestMapping(path="/driver")
public class DriverController {

  private final DriverService driverService;

  @Autowired
  public DriverController(DriverService driverService) {
      this.driverService = driverService;
  }
  //Handles HTTP POST requests to add driver data.
  @PostMapping("/add")
  public String addDriver(String id, String name, String status) {
      boolean isValid = driverService.addDriver(id, name, status);

      if(isValid)
        return "Driver data added successfully!";
      return "Driver data not added";
  }

  @PostMapping("/update")
  public String updateDriver(String id, String name, String status) {
      boolean isValid = driverService.updateDriver(id, name, status);
      if(isValid)
        return "Driver data updated successfully!";
      return "Driver data not updated";
  }

  //Handles HTTP DELETE requests to delete driver data.
  @DeleteMapping("/delete")
  public String deleteDriver(String id) {
      boolean isValid = driverService.deleteDriverById(id);
      if(isValid)
        return "Driver data deleted successfully!";
      return "Driver data not deleted";
  }
}