package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SpacesService {

    @Autowired
    private DataSource dataSource;

    // Function to add Spaces data
    public boolean addSpaces(int spaceNumber, String spaceType, String availabilityStatus, String zoneID, int lotID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = -1;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            System.out.println("Data Recieved"+spaceNumber);
            
            String sql = "INSERT INTO Spaces(SpaceNumber, spaceType, availabilityStatus, ZoneID, LotID) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, spaceNumber);
            preparedStatement.setString(2, spaceType);
            preparedStatement.setString(3, availabilityStatus);
            preparedStatement.setString(4, zoneID);
            preparedStatement.setInt(5, lotID);

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

    public boolean updateSpaces(int spaceNumber, String spaceType, String availabilityStatus, String zoneID, int lotID) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement upsertStatement = null;
        ResultSet resultSet = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            String checkSql = "SELECT * FROM Spaces WHERE SpaceNumber = ? AND ZoneID = ? AND LotID = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setInt(1, spaceNumber);
            checkStatement.setString(2, zoneID);
            checkStatement.setInt(3, lotID);

            resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                String updateSql = "UPDATE Spaces SET spaceType = ?, availabilityStatus = ? WHERE SpaceNumber = ? AND ZoneID = ? AND LotID = ?";
                upsertStatement = connection.prepareStatement(updateSql);
                upsertStatement.setString(1, spaceType != null ? spaceType : resultSet.getString("spaceType"));
                upsertStatement.setString(2, availabilityStatus != null ? availabilityStatus : resultSet.getString("availabilityStatus"));

                upsertStatement.setInt(3, spaceNumber);
                upsertStatement.setString(4, zoneID);
                upsertStatement.setInt(5, lotID);

                int affectedRows = upsertStatement.executeUpdate();
                System.out.println("Rows updated: " + affectedRows);
            } else {
                System.out.println("The record does not exist");
                isValid = false;
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

    public boolean deleteSpaceById(int spaceNumber, String zoneID, int lotID) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();

            
            String deleteSql = "DELETE FROM Spaces WHERE SpaceNumber = ? AND ZoneID = ? AND LotID = ?   ";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, spaceNumber);
            deleteStatement.setString(2, zoneID);
            deleteStatement.setInt(3, lotID);

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

