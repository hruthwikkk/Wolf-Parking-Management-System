package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CitationsService {

    @Autowired
    private DataSource dataSource;

    // Function to add Citation data
    public boolean addCitation(String citationNumber, String category, java.sql.Timestamp timestampIssued, String paymentStatus, String driverID, String licenseNumber, String lotName, String model, String color) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int affectedRows = -1;
        String spaceType = "";
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
    
            String sql1 = "SELECT spaceType FROM Permits WHERE licenseNumber = ?";
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, licenseNumber);
            resultSet = preparedStatement1.executeQuery();

            
            if (resultSet.next()) {
                spaceType = resultSet.getString("spaceType");
            }
            double fee = calculateFee(category);
            if ("handicap".equalsIgnoreCase(spaceType)) {
                fee *= 0.5; // Apply 50% discount
            }
    
            
            String sql2 = "INSERT INTO Citations(citationNumber, category, timestampIssued, paymentStatus, fee, driverID, licenseNumber, lotName, model, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, citationNumber);
            preparedStatement.setString(2, category);
            preparedStatement.setTimestamp(3, timestampIssued);
            preparedStatement.setString(4, paymentStatus);
            preparedStatement.setDouble(5, fee);
            preparedStatement.setString(6, driverID);
            preparedStatement.setString(7, licenseNumber);
            preparedStatement.setString(8, lotName);
            preparedStatement.setString(9, model);
            preparedStatement.setString(10, color);
    
            affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
    
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

    private double calculateFee(String category) {
        switch (category) {
            case "Invalid Permit":
                return 25.0;
            case "Expired Permit":
                return 30.0;
            case "No Permit":
                return 40.0;
            default:
                return 0.0;
        }
    }

    public boolean deleteCitationById(String citationNumber) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            String deleteSql = "DELETE FROM Citations WHERE citationNumber = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, citationNumber);

            int affectedRows = deleteStatement.executeUpdate();
            System.out.println("Rows deleted: " + affectedRows);
            if(affectedRows==0) {
                System.out.println("Citation not found");
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

    public boolean updateCitationPaymentStatus(String citationNumber, String newPaymentStatus) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
    
            // Disable autocommit to start a transaction
            connection.setAutoCommit(false);

            String updateSql = "UPDATE Citations SET PaymentStatus = ? WHERE CitationNumber = ?";
            updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, newPaymentStatus);
            updateStatement.setString(2, citationNumber);
    
            int affectedRows = updateStatement.executeUpdate();
            System.out.println("Rows updated: " + affectedRows);
    
            // Commit the transaction
            connection.commit();
    
        } catch (SQLException e) {
            // Rollback the transaction in case of exception
            isValid = false;
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
    
            e.printStackTrace();
        } finally {
            
            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (connection != null) {
                try {
                    // Restore autocommit to true and close the connection
                    connection.setAutoCommit(true);
                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isValid;
    }

    public List<Map<String, Object>> generateCitationReport(int parkingLotId, String startDate, String endDate) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> citationReport = new ArrayList<>();

        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            String sql = "SELECT LotName, COUNT(*) AS CitationCount " +
             "FROM Citations " +
             "WHERE LotName IN (SELECT Name FROM ParkingLot WHERE ID = ?) " +
             "AND timestampIssued BETWEEN ? AND ? " +
             "GROUP BY LotName";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, parkingLotId);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> tuple = new HashMap<>();
                tuple.put("LotName", resultSet.getString("LotName"));
                tuple.put("CitationCount", resultSet.getInt("CitationCount"));
                citationReport.add(tuple);
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

        return citationReport;
    }

    public boolean payCitation(String driverNumber, String citationNumber) {
        Connection connection = null;
        PreparedStatement insertCompensatesStatement = null;
        PreparedStatement updateCitationPaymentStatusStatement = null;
        PreparedStatement retrieveDriverIdStatement = null;
        ResultSet resultSet = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
    
            // Disable autocommit to start a transaction
            connection.setAutoCommit(false);
    
            String retrieveDriverIdSql = "SELECT DriverID FROM Citations WHERE CitationNumber = ?";
            retrieveDriverIdStatement = connection.prepareStatement(retrieveDriverIdSql);
            retrieveDriverIdStatement.setString(1, citationNumber);
            resultSet = retrieveDriverIdStatement.executeQuery();

            if (resultSet.next()) {
                String retrievedDriverId = resultSet.getString("DriverID");
                if (retrievedDriverId.equals(driverNumber)) {
                    String insertCompensatesSql = "INSERT INTO Compensates (DriverID, CitationNumber) VALUES (?, ?)";
                    insertCompensatesStatement = connection.prepareStatement(insertCompensatesSql, Statement.RETURN_GENERATED_KEYS);
                    insertCompensatesStatement.setString(1, driverNumber);
                    insertCompensatesStatement.setString(2, citationNumber);
                    insertCompensatesStatement.executeUpdate();
    
                    // Retrieve the auto-generated payment ID
                    ResultSet generatedKeys = insertCompensatesStatement.getGeneratedKeys();
                    int paymentId = -1;
                    if (generatedKeys.next()) {
                        paymentId = generatedKeys.getInt(1);
                    }
    
                    String updateCitationPaymentStatusSql = "UPDATE Citations SET PaymentStatus = 'Paid' WHERE CitationNumber = ?";
                    updateCitationPaymentStatusStatement = connection.prepareStatement(updateCitationPaymentStatusSql);
                    updateCitationPaymentStatusStatement.setString(1, citationNumber);
                    updateCitationPaymentStatusStatement.executeUpdate();
    
                    // Commit the transaction
                    connection.commit();
    
                    System.out.println("Payment ID generated: " + paymentId);
                } else {
                    System.out.println("Driver ID mismatch. Rolling back the transaction.");
                    connection.rollback();
                    isValid = false;
                }
            } else {
                System.out.println("Driver ID not found for the given CitationNumber.");
                connection.rollback();
                isValid = false;
            }
    
        } catch (SQLException e) {
            // Rollback the transaction in case of exception
            isValid = false;
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
    
            e.printStackTrace();
        } finally {
            
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (retrieveDriverIdStatement != null) {
                try {
                    retrieveDriverIdStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (insertCompensatesStatement != null) {
                try {
                    insertCompensatesStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (updateCitationPaymentStatusStatement != null) {
                try {
                    updateCitationPaymentStatusStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (connection != null) {
                try {
                    // Restore autocommit to true and close the connection
                    connection.setAutoCommit(true);
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
