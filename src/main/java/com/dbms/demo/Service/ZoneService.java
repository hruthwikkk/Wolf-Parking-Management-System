package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class ZoneService {

    @Autowired
    private DataSource dataSource;

    // Function to add Zones data
    public boolean addZones(String id, int lotID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = -1;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            System.out.println("Data Recieved"+ id);
            
            String sql = "INSERT INTO Zone(ID, LotID) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, lotID);

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

    // Function to delete Zones data
    public boolean deleteZoneById(String id, int lotID) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            String deleteSql = "DELETE FROM Zone WHERE ID = ? AND LotID = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, id);
            deleteStatement.setInt(2, lotID);

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

