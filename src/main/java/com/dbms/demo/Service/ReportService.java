package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.demo.DTO.PermitInfoDTO;
import com.dbms.demo.DTO.ZoneInfoDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;
    // Function to get Zone information of all lots
    public List<ZoneInfoDTO> getZoneInfoForAllLots() {
        List<ZoneInfoDTO> zoneInfoList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT Zone.ID AS zoneId, Lot.Name AS lotName " +
                    "FROM Zone, ParkingLot Lot " +
                    "WHERE Zone.LotID = Lot.ID " +
                    "ORDER BY Lot.Name";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Map<String, List<String>> lotZoneMap = new HashMap<>();

                    while (resultSet.next()) {
                        String zoneId = resultSet.getString("zoneId");
                        String lotName = resultSet.getString("lotName");

                        lotZoneMap.computeIfAbsent(lotName, k -> new ArrayList<>()).add(zoneId);
                    }
                    for (Map.Entry<String, List<String>> entry : lotZoneMap.entrySet()) {
                        String lotName = entry.getKey();
                        List<String> zones = entry.getValue();

                        ZoneInfoDTO zoneInfoDTO = new ZoneInfoDTO(lotName, zones);
                        zoneInfoList.add(zoneInfoDTO);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zoneInfoList;
    }

    //Function to get Violating Cars Count
    public int getViolatingCarsCount() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            String sql = "SELECT Count( DISTINCT licenseNumber) FROM Citations c WHERE c.PaymentStatus = 'DUE'";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    public List<PermitInfoDTO> getPermitInfoByIdOrPhone(String driverIdOrPhone) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PermitInfoDTO> permitInfoList = new ArrayList<>();

        try {
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            String sql = "SELECT p.PermitType, p.ID, p.SpaceType, p.StartDate, p.ExpirationDate, p.ExpirationTime, p.DriverID " +
                    "FROM Permits p JOIN Driver d ON d.ID = p.DriverID " +
                    "WHERE p.DriverID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, driverIdOrPhone);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PermitInfoDTO permitInfo = new PermitInfoDTO();
                permitInfo.setPermitType(resultSet.getString("PermitType"));
                permitInfo.setId(resultSet.getString("ID"));
                permitInfo.setSpaceType(resultSet.getString("SpaceType"));
                permitInfo.setStartDate(resultSet.getDate("StartDate"));
                permitInfo.setExpirationDate(resultSet.getDate("ExpirationDate"));
                permitInfo.setExpirationTime(resultSet.getTime("ExpirationTime"));
                permitInfo.setDriverId(resultSet.getString("DriverID"));

                permitInfoList.add(permitInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return permitInfoList;
    }
}
