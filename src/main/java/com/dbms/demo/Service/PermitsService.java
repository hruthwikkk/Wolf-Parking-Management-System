package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class PermitsService {

    @Autowired
    private DataSource dataSource;

    // Function to add permit data
    public boolean addPermit(String ID, String spaceType, Date startDate, Date expirationDate, Time expirationTime, String permitType, String driverID, String zoneID, String licenseNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = -1;
        boolean isValid = true;
        try {
            
        System.out.println("Getting connection");
        connection = dataSource.getConnection();
        String userType = getUserType(driverID); 
        int permitCount = getPermitCountForUser(driverID); 

        if ("S".equalsIgnoreCase(userType) || "V".equalsIgnoreCase(userType)) {
            if (permitCount >= 1) {
                System.out.println("Error: Students and visitors can only have one permit at a time.");
                isValid = false;
            }
        } else if ("E".equalsIgnoreCase(userType)) {
            if (permitCount >= 2) {
                System.out.println("Error: Employees can only have up to two permits at a time.");
                isValid = false;
            }
        } else {
            System.out.println("Error: Invalid user type.");
            isValid = false;
        }

        if (isValid) {
            
            String sql = "INSERT INTO Permits(ID, spaceType, startDate, expirationDate, expirationTime, permitType, LicenseNumber, ZoneID, DriverID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            preparedStatement.setString(2, spaceType);
            preparedStatement.setDate(3, startDate);
            preparedStatement.setDate(4, expirationDate);
            preparedStatement.setTime(5, expirationTime);
            preparedStatement.setString(6, permitType);
            preparedStatement.setString(7, licenseNumber);
            preparedStatement.setString(8, zoneID);
            preparedStatement.setString(9, driverID);

            affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            isValid = false;
        } finally {
            
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
        return isValid;
    }

    private String getUserType(String driverID) {
        String userType = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            connection = dataSource.getConnection();

            String sql = "SELECT Status FROM Driver WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, driverID);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userType = resultSet.getString("Status");
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return userType;
    }


    private int getPermitCountForUser(String driverID) {
        int permitCount = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            connection = dataSource.getConnection();

            String sql = "SELECT COUNT(*) AS PermitCount FROM Permits WHERE DriverID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, driverID);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                permitCount = resultSet.getInt("PermitCount");
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return permitCount;
    }


    public boolean updatePermit(String ID, String spaceType, Date startDate, Date expirationDate, Time expirationTime, String permitType) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement upsertStatement = null;
        ResultSet resultSet = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            String checkSql = "SELECT * FROM Permits WHERE ID = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, ID);

            resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                String updateSql = "UPDATE Permits SET spaceType = ?, startDate = ?, expirationDate = ?, expirationTime = ?, permitType = ? WHERE ID = ?";
                upsertStatement = connection.prepareStatement(updateSql);
                upsertStatement.setString(1, spaceType != null ? spaceType : resultSet.getString("spaceType"));
                upsertStatement.setDate(2, startDate != null ? startDate : Date.valueOf(resultSet.getString("startDate")));
                upsertStatement.setDate(3, expirationDate != null ? expirationDate : Date.valueOf(resultSet.getString("expirationDate")));
                upsertStatement.setTime(4, expirationTime != null ? expirationTime : Time.valueOf(resultSet.getString("expirationTime")));
                upsertStatement.setString(5, permitType != null ? permitType : resultSet.getString("permitType"));
                upsertStatement.setString(6, ID);


                int affectedRows = upsertStatement.executeUpdate();
                System.out.println("Rows updated: " + affectedRows);
            } else {
                System.out.println("The record does not exist");
                isValid = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            isValid = false;
        } finally {
            
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (checkStatement != null) {
                try {
                    checkStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (upsertStatement != null) {
                try {
                    upsertStatement.close();
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
        return isValid;
    }

    public boolean deletePermitById(String ID) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            
            String deleteSql = "DELETE FROM Permits WHERE ID = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, ID);


            int affectedRows = deleteStatement.executeUpdate();
            System.out.println("Rows deleted: " + affectedRows);
            if(affectedRows == 0){
                System.out.println("Data not found");
                isValid = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            isValid = false;
        } finally {
            
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
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
        return isValid;
    }

    public int checkValidPermit(String licenseNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int isValid = 0;

        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            String sql = "SELECT IF(EXISTS (SELECT 1 FROM Permits p " +
                    "JOIN Occupies o ON p.LicenseNumber = o.VehicleLicenseNumber " +
                    "JOIN Spaces s ON o.SpaceNumber = s.SpaceNumber AND o.ZoneID = s.ZoneID AND o.LotID = s.LotID " +
                    "WHERE p.LicenseNumber=? AND p.ZoneID = o.ZoneID " +
                    "AND p.ExpirationDate>=CURDATE() AND p.SpaceType = s.SpaceType LIMIT 1), 1, 0) as 'isValidPermit'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, licenseNumber);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValid = resultSet.getInt("isValidPermit");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
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

        return isValid;
    }


}

