package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class DriverService {

    @Autowired
    private DataSource dataSource;

    // Function to add driver data
    public boolean addDriver(String id, String name, String status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = -1;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            System.out.println("Data Recieved"+ id);
            
            String sql = "INSERT INTO Driver(ID, Name, Status) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, status);
    
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

    // Function to update driver data
    public boolean updateDriver(String id, String name, String status) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet resultSet = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            String checkSql = "SELECT * FROM Driver WHERE ID = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, id);
            resultSet = checkStatement.executeQuery();
    
            if (resultSet.next()) {
                
                String updateSql = "UPDATE Driver SET Name = ?, Status = ? WHERE ID = ?";
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, name != null ? name : resultSet.getString("Name"));
                updateStatement.setString(2, status != null ? status : resultSet.getString("Status"));
                updateStatement.setString(3, id);
    
                int affectedRows = updateStatement.executeUpdate();
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
    
            if (updateStatement != null) {
                try {
                    updateStatement.close();
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
    
    public boolean deleteDriverById(String id) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
    
            
            String deleteSql = "DELETE FROM Driver WHERE ID = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, id);
    
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

