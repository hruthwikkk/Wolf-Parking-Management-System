package com.dbms.demo.Controller;

import com.dbms.demo.DTO.PermitInfoDTO;
import com.dbms.demo.DTO.ZoneInfoDTO;
import com.dbms.demo.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    private final ReportService reportService;

    @Autowired
    public ReportsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/zoneInfoForAllLots")
    public List<ZoneInfoDTO> getZoneInfoForAllLots() {
        return reportService.getZoneInfoForAllLots();
    }

    @GetMapping("/violatingCarsCount")
    public int getViolatingCarsCount() {
        return reportService.getViolatingCarsCount();
    }

    @GetMapping("/permitInfoByIdOrPhone")
    public List<PermitInfoDTO> getPermitInfoByIdOrPhone(String driverID) {
        return reportService.getPermitInfoByIdOrPhone(driverID);
    }
}

