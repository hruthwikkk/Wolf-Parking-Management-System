package com.dbms.demo.DTO;

import java.util.List;

public class ZoneInfoDTO {
    private String lotName;
    private List<String> zoneIds;

    public ZoneInfoDTO(String lotName, List<String> zoneIds) {
        this.lotName = lotName;
        this.zoneIds = zoneIds;
    }

    public String getLotName() {
        return lotName;
    }

    public List<String> getZoneIds() {
        return zoneIds;
    }
}
