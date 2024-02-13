package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class VehicleService {

    @Autowired
    private DataSource dataSource;

    // Function to add vehicle data
    public boolean addVehicle(String licenseNumber, String model, String color, String manufacturer, int year, String driverID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = -1;
        boolean isValid = true;
    
        try {
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            System.out.println("Data Received " + licenseNumber);
    
            // Start transaction
            connection.setAutoCommit(false);
    
            String sql = "INSERT INTO Vehicles(licenseNumber, color, driverID, manufacturer, model, year) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, licenseNumber);
            preparedStatement.setString(2, color);
            preparedStatement.setString(3, driverID);
            preparedStatement.setString(4, manufacturer);
            preparedStatement.setString(5, model);
            preparedStatement.setInt(6, year);
    
            affectedRows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
    
            // Commit the transaction if everything is successful
            connection.commit();
    
        } catch (SQLException e) {
            // Rollback the transaction in case of an exception
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    
            e.printStackTrace();
            isValid = false;
    
        } finally {
            try {
                // Restore auto-commit mode and close resources
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                    System.out.println("Connection closed");
                }
    
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isValid;
    }
    

    public boolean deleteVehicleById(String licenseNumber) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            String deleteSql = "DELETE FROM Vehicles WHERE licenseNumber = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, licenseNumber);


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


}

