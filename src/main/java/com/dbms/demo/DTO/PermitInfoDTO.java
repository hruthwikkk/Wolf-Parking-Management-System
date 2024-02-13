package com.dbms.demo.DTO;

import java.util.Date;

public class PermitInfoDTO {

    private String permitType;
    private String id;
    private String spaceType;
    private Date startDate;
    private Date expirationDate;
    private java.sql.Time expirationTime;
    private String driverId;

    public PermitInfoDTO() {
    }

    public PermitInfoDTO(String permitType, String id, String spaceType, Date startDate, Date expirationDate, java.sql.Time expirationTime, String driverId) {
        this.permitType = permitType;
        this.id = id;
        this.spaceType = spaceType;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.expirationTime = expirationTime;
        this.driverId = driverId;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public java.sql.Time getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(java.sql.Time expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
