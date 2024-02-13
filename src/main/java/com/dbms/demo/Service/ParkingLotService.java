package com.dbms.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class ParkingLotService {

    @Autowired
    private DataSource dataSource;

    // Function to add Parking Lot data
    public boolean addLot(int ID, String name, String address) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = -1;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            System.out.println("Data Recieved"+ID);
            
            String sql = "INSERT INTO ParkingLot(ID, name, address) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, address);

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

    // Function to update Lot data
    public boolean updateLot(int ID, String name, String address) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement upsertStatement = null;
        ResultSet resultSet = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            String checkSql = "SELECT * FROM ParkingLot WHERE ID = ?";
            checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setInt(1, ID);

            resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                String updateSql = "UPDATE ParkingLot SET name = ?, address = ? WHERE ID = ?";
                upsertStatement = connection.prepareStatement(updateSql);
                upsertStatement.setString(1, name != null ? name : resultSet.getString("name"));
                upsertStatement.setString(2, address != null ? address : resultSet.getString("address"));
                upsertStatement.setInt(3, ID);


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

    public boolean deleteLotById(int ID) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        boolean isValid = true;
        try {
            
            System.out.println("Getting connection");
            connection = dataSource.getConnection();
            String deleteSql = "DELETE FROM ParkingLot WHERE ID = ?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, ID);


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

